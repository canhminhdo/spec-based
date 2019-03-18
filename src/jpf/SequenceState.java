package jpf;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.CharArrayFields;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.Heap;
import gov.nasa.jpf.vm.NamedFields;
import gov.nasa.jpf.vm.ReferenceArrayFields;
import gov.nasa.jpf.vm.VM;
import main.Cell;
import main.Channel;
import main.Pair;

public class SequenceState extends ListenerAdapter {
	
	private static final int DEPTH = 1000;
	private static final int BOUND = 10;
	private static int COUNT = 0;
	private static int PRINT_COUNT = 0;
	private static final String TXT_EXT = "txt";
	private static final String OUT_FILENAME_NO_EXT = "jpf-sequence-state";

	private BufferedWriter graph;
	private String out_filename = OUT_FILENAME_NO_EXT + "." + TXT_EXT;
	
	private static int STARTUP = 1;
	private Map<String,Integer> lookupTable = new HashMap<String,Integer>();
	private Node<Configuration<String>> root;
	private Node<Configuration<String>> lastNode;
	
	public SequenceState(Config conf, JPF jpf) {
		root = new Node<Configuration<String>>();
		lastNode = root;
	}
	
	public void try_seq(Node<Configuration<String>> node, ArrayList<Configuration<String>> seq) throws IOException {
		seq.add(node.getData());
		if (node.isLeaf()) {
			// Ending -> print sequence of state here
			graph.write(seq.toString());
			graph.newLine();
			PRINT_COUNT ++;
			Logger.log(PRINT_COUNT);
		} else {
			for (Node<Configuration<String>> child : node.getChildren()) {
				try_seq(child, seq);
			}
		}
	}
	
	/**
	 * got the next state
	 * 
	 * Note - this will be notified before any potential propertyViolated, in which
	 * case the currentError will be already set
	 */
	@Override
	public void stateAdvanced(Search search) {
		if (SequenceState.STARTUP == 1) {
			SequenceState.STARTUP ++;
			startup(search.getVM());
		}
		Configuration<String> config = getConfiguration(search);
		if (config == null) {
			// Finish program
			search.requestBacktrack();
			COUNT ++;
		} else {
			lastNode = lastNode.addChild(new Node<Configuration<String>>(config));
			if (search.isEndState() || !search.isNewState()) {
				// End state or is not new state. JPF will back track
				COUNT ++;
			} if (search.getDepth() >= DEPTH) {
				// current depth is greater than DEPTH, back track
				search.requestBacktrack();
				COUNT ++;
			}
		}
		if (COUNT >= BOUND) {
			// terminate when number of sequence of states reach to BOUND
			search.terminate();
		}
	}

	@Override
	public void stateBacktracked(Search search) {
		lastNode = lastNode.getParent();
	}

	@Override
	public void searchStarted(Search search) {
		try {
			beginGraph();
		} catch (IOException e) {
		}
	}

	@Override
	public void searchFinished(Search search) {
		try {
			Logger.log("Start writing to file: " +  out_filename);
			try_seq(root, new ArrayList<Configuration<String>>());
			endGraph();
			Logger.log("Finished !!!");
		} catch (IOException e) {
		}
	}

	private void beginGraph() throws IOException {
		graph = new BufferedWriter(new FileWriter(out_filename));
		graph.write("jpf_sequence_states {");
		graph.newLine();
	}

	private void endGraph() throws IOException {
		graph.write("}");
		graph.newLine();
		graph.close();
	}
	
	private void showLookupTable() {
		Logger.info("Looup Table");
		Logger.info("-----------");
		for (Map.Entry< String,Integer > entry:lookupTable.entrySet()) { 
			Logger.info(entry.getKey() + ":" + entry.getValue());
		}
		Logger.info("-----------");
	}
	
	private void startup(VM vm) {
		for (ElementInfo ei : vm.getHeap().liveObjects()) {
			String name = ei.getClassInfo().getName();
			if (name.contains("Sender") || name.contains("Receiver")) {
				lookupTable.put(name, ei.getObjectRef());
			}
		}
		showLookupTable();
	}
	
	private Configuration<String> getConfiguration(Search search) {
		Configuration<String> config = new Configuration<String>();
		config.setStateId(search.getStateId());
		config.setDeep(search.getDepth());
		Heap heap = search.getVM().getHeap();
		{
			// Sender
			ElementInfo ei = heap.get(lookupTable.get("main.Sender"));
			if (ei == null) {
				return null;
			}
			FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
			for (FieldInfo fi : fis) {
				switch (fi.getName()) {
					case "flag1":
						// flag1 -> done
						ElementInfo ei_flag1 = (ElementInfo)fi.getValueObject(ei.getFields());
						Boolean flag1 = getFlag(ei_flag1);
						config.setFlag1(flag1);
						break;
					case "finish":
						// finish -> done
						ElementInfo ei_finish = (ElementInfo)fi.getValueObject(ei.getFields());
						Cell<Boolean> finish = getFinish(ei_finish);
						config.setFinish(finish);
						break;
					case "packetsToBeSent":
						// packetsToBeSent -> done
						ElementInfo ei_packetsToBeSent = (ElementInfo)fi.getValueObject(ei.getFields());
						ArrayList<String> packetsToBeSent = getPacketsToBeSent(search.getVM(), ei_packetsToBeSent);
						config.setPacketsToBeSent(packetsToBeSent);
					case "channel2":
						// channel2 -> done
						ElementInfo ei_channel2 = (ElementInfo)fi.getValueObject(ei.getFields());
						if (ei_channel2.getClassInfo().getInstanceField("bound") == null) {
							// TODO :: channel2 is empty
						} else {
							// int nop = ei_channel2.getIntField("nop");
							int bound = ei_channel2.getIntField("bound");
							ElementInfo ei_queue = (ElementInfo) ei_channel2.getFieldValueObject("queue");
							Channel<Boolean> channel2 = getChannelBoolean(ei_queue, bound);
							config.setChannel2(channel2);
						}
						break;
					case "channel1":
						// channel 1 -> done
						ElementInfo ei_channel1 = (ElementInfo)fi.getValueObject(ei.getFields());
						if (ei_channel1.getClassInfo().getInstanceField("bound") == null) {
							// TODO :: channel1 is empty
						} else {
							// int nop = ei_channel1.getIntField("nop");
							int bound = ei_channel1.getIntField("bound");
							ElementInfo ei_queue = (ElementInfo) ei_channel1.getFieldValueObject("queue");
							Channel<Pair<String,Boolean>> channel1 = getChannelPair(ei_queue, bound);
							config.setChannel1(channel1);
						}
					default:
						break;
				}
			}
		}
		{
			// Receiver
			ElementInfo ei = heap.get(lookupTable.get("main.Receiver"));
			if (ei == null) {
				return null;
			}
			FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
			for (FieldInfo fi : fis) {
				switch (fi.getName()) {
					case "flag2":
						// flag2 -> done
						ElementInfo ei_flag2 = (ElementInfo)fi.getValueObject(ei.getFields());
						Boolean flag2 = getFlag(ei_flag2);
						config.setFlag2(flag2);
						break;
					
					case "packetsReceived":
						// packetsReceived -> done
						ElementInfo ei_packetsReceived = (ElementInfo)fi.getValueObject(ei.getFields());
						ArrayList<String> packetsReceived = getPacketsReceived(search.getVM(), ei_packetsReceived);
						config.setPacketsReceived(packetsReceived);
					default:
						break;
				}
			}
		}
		return config;
	}
	
	private void showFieldInfos(ElementInfo ei) {
		FieldInfo[] fis = ei.getClassInfo().getDeclaredInstanceFields();
		Logger.log("Length: " + ei.getClassInfo().getNumberOfDeclaredInstanceFields());
		for (FieldInfo fi : fis) {
			Logger.log(fi.getName() + "-" + fi.getType() + " -> ref: " + fi.isReference());
		}
	}
	
	private Channel<Boolean> getChannelBoolean(ElementInfo ei_queue, int bound) {
		Channel<Boolean> channel2 = new Channel<Boolean>(bound);
		while (true) {
			if (ei_queue.getClassInfo().getInstanceField("head") == null) {
				break;
			}
			ElementInfo ei_head = (ElementInfo) ei_queue.getFieldValueObject("head");
			channel2.put(((NamedFields)ei_head.getFields()).getBooleanValue(0));
			ei_queue = (ElementInfo) ei_queue.getFieldValueObject("tail");
		}
		return channel2;
	}
	
	private Channel<Pair<String, Boolean>> getChannelPair(ElementInfo ei_queue, int bound) {
		Channel<Pair<String,Boolean>> channel1 = new Channel<Pair<String,Boolean>>(bound);
		while (true) {
			if (ei_queue.getClassInfo().getInstanceField("head") == null) {
				break;
			}
			ElementInfo ei_head = (ElementInfo) ei_queue.getFieldValueObject("head");
			// elt1
			ElementInfo ei_elt1 = (ElementInfo) ei_head.getFieldValueObject("elt1");
			ElementInfo ei_value1 = (ElementInfo) ei_elt1.getFieldValueObject("value");
			CharArrayFields caf_elt1 = (CharArrayFields) ei_value1.getArrayFields();
			String elt1 = String.valueOf((char[])caf_elt1.getValues());
			// elt2
			ElementInfo ei_elt2 = (ElementInfo) ei_head.getFieldValueObject("elt2");
			boolean elt2 = ei_elt2.getBooleanField("value");
			channel1.put(new Pair<String,Boolean>(elt1,elt2));
			ei_queue = (ElementInfo) ei_queue.getFieldValueObject("tail");
		}
		return channel1;
	}
	
	private ArrayList<String> getPacketsReceived(VM vm, ElementInfo ei_packetsReceived) {
		ElementInfo ei_a = (ElementInfo) ei_packetsReceived.getFieldValueObject("a");
		ArrayList<String> packetsReceived = new ArrayList<String>();
		if (ei_a != null) {
			ReferenceArrayFields raf = (ReferenceArrayFields) ei_a.getArrayFields();
			for (int i : (int[])raf.getValues()) {
				ElementInfo ei_rf = vm.getHeap().get(i);
				ElementInfo ei_value = (ElementInfo) ei_rf.getFieldValueObject("value");
				CharArrayFields caf = (CharArrayFields) ei_value.getArrayFields();
				packetsReceived.add(String.valueOf((char[])caf.getValues()));
			}
		}
		return packetsReceived;
	}
	
	private ArrayList<String> getPacketsToBeSent(VM vm, ElementInfo ei_packetsToBeSent) {
		ElementInfo ei_a = (ElementInfo) ei_packetsToBeSent.getFieldValueObject("a");
		ArrayList<String> packetsToBeSent = new ArrayList<String>();
		if (ei_a != null) {
			ReferenceArrayFields raf = (ReferenceArrayFields) ei_a.getArrayFields();
			for (int i : (int[])raf.getValues()) {
				ElementInfo ei_rf = vm.getHeap().get(i);
				ElementInfo ei_value = (ElementInfo) ei_rf.getFieldValueObject("value");
				CharArrayFields caf = (CharArrayFields) ei_value.getArrayFields();
				packetsToBeSent.add(String.valueOf((char[])caf.getValues()));
			}
		}
		return packetsToBeSent;
	}
	
	private Boolean getFlag(ElementInfo ei_info) {
		NamedFields nf_flag = (NamedFields)ei_info.getFields();
		return nf_flag.getBooleanValue(0);
	}
	
	private Cell<Boolean> getFinish(ElementInfo ei_finish) {
		ElementInfo ei_element = (ElementInfo) ei_finish.getFieldValueObject("element");
		NamedFields nf_element = (NamedFields) ei_element.getFields();
		return new Cell<Boolean>(nf_element.getBooleanValue(0));
	}
}
