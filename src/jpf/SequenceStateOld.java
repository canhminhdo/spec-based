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
import jpf.abp.Configuration;
import main.Cell;
import main.Channel;
import main.Pair;
import model.TestCase;
import service.TestCaseService;

public class SequenceStateOld extends ListenerAdapter {
	
	private static final int DEPTH = 50;
	private static final int BOUND = 1000;
	private static boolean DEPTH_FLAG = true;
	private static boolean BOUND_FLAG = false;
	private static int COUNT = 0;
	private static int PRINT_COUNT = 0;
	private static final String TXT_EXT = "txt";
	private static final String OUT_FILENAME_NO_EXT = "./maude/data";

	private BufferedWriter graph;
	private String out_filename = OUT_FILENAME_NO_EXT + "." + TXT_EXT;
	
	private static int STARTUP = 1;
	private Map<String,Integer> lookupTable = new HashMap<String,Integer>();
	private Node<Configuration<String>> root;
	private Node<Configuration<String>> lastNode;
	
	public SequenceStateOld(Config conf, JPF jpf) {
		root = new Node<Configuration<String>>();
		lastNode = root;
		if (!TestCaseService.truncate()) {
			Logger.error("Can't truncate `test_cases` table");
		}
	}
	
	public void try_seq(Node<Configuration<String>> node, ArrayList<Configuration<String>> seq) throws IOException {
		if (!node.isRoot()) {
			if (seq.isEmpty()) {
				seq.add(node.getData());
			} else {
				Configuration<String> lastElement = seq.get(seq.size() - 1);
				if (!lastElement.equals(node.getData())) {
					seq.add(node.getData());
				} else {
					// Duplicated !!!
				}
			}
		}
		if (node.isLeaf()) {
			// Ending -> print sequence of state here
			graph.write(seqToString(seq) + " , ");
			graph.newLine();
			PRINT_COUNT ++;
		} else {
			int seq_size = seq.size();
			for (Node<Configuration<String>> child : node.getChildren()) {
				try_seq(child, seq);
				while (seq.size() > 0 && seq.size() > seq_size) {
					seq.remove(seq.size() - 1);
				}
			}
		}
	}
	
	public void try_seq_log(Node<Configuration<String>> node, ArrayList<Configuration<String>> seq, ArrayList<TestCase> list) throws IOException {
		if (!node.isRoot()) {
			list.add(convert(node.getData()));
			if (seq.isEmpty()) {
				seq.add(node.getData());
			} else {
				Configuration<String> lastElement = seq.get(seq.size() - 1);
				if (!lastElement.equals(node.getData())) {
					seq.add(node.getData());
				} else {
					// Duplicated !!!
				}
			}
		}
		if (node.isLeaf()) {
			// Ending -> print sequence of state here
			graph.write(seqToString(seq) + " , ");
			graph.newLine();
			PRINT_COUNT ++;
			
			// save to database here
			if (TestCaseService.insertBatch(list, PRINT_COUNT)) {
				// Insert done
			} else {
				Logger.error("Can't insert");
			}
		} else {
			int seq_size = seq.size();
			int list_size = list.size();
			for (Node<Configuration<String>> child : node.getChildren()) {
				try_seq_log(child, seq, list);
				while (seq.size() > 0 && seq.size() > seq_size) {
					seq.remove(seq.size() - 1);
				}
				while (list.size() > 0 && list.size() > list_size) {
					list.remove(list.size() - 1);
				}
			}
		}
	}
	
	public TestCase convert(Configuration<String> config) {
		TestCase testCase = new TestCase();
		
		testCase.setStateId(config.getStateId());
		testCase.setDepth(config.getDepth());
		testCase.setPacketsToBeSent(config.getPacketsToBeSent().toString());
		testCase.setPacketsReceived(config.getAbpBuf());
		testCase.setChannel1(config.getChannel1().toString());
		testCase.setChannel2(config.getChannel2().toString());
		testCase.setIndex(config.getIndex());
		testCase.setFinish(config.getFinish().toString());
		testCase.setFlag1(config.getFlag1());
		testCase.setFlag2(config.getFlag2());
		
		return testCase;
	}
	
	public String seqToString(ArrayList<Configuration<String>> seq) {
		if (seq.size() == 0) {
			return "nil";
		}
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(seq.get(0));
		for (int i = 1; i < seq.size(); i ++) {
			sb.append(" | ");
			sb.append(seq.get(i));
		}
		sb.append(" | nil)");
		return sb.toString();
	}
	
	/**
	 * got the next state
	 * 
	 * Note - this will be notified before any potential propertyViolated, in which
	 * case the currentError will be already set
	 */
	@Override
	public void stateAdvanced(Search search) {
		if (SequenceStateOld.STARTUP == 1) {
			SequenceStateOld.STARTUP ++;
			startup(search.getVM());
		}
		Configuration<String> config = getConfiguration(search);
		if (config == null) {
			// Finish program
			search.requestBacktrack();
			Logger.log("Finish program at " + search.getDepth());
			COUNT ++;
		} else {
			lastNode = lastNode.addChild(new Node<Configuration<String>>(config));
			if (search.isEndState() || !search.isNewState()) {
				// End state or is not new state (visited state). JPF will back track automatically
				COUNT ++;
			} if (DEPTH_FLAG && search.getDepth() >= DEPTH) {
				// current depth is greater than DEPTH, back track
				search.requestBacktrack();
				COUNT ++;
			}
		}
		if (BOUND_FLAG && COUNT >= BOUND) {
			// terminate when number of sequence of states reach to BOUND
			search.terminate();
		}
	}

	@Override
	public void stateBacktracked(Search search) {
		while (lastNode.getData() != null && lastNode.getData().getStateId() != search.getStateId()) {
			lastNode = lastNode.getParent();
		}
//		Logger.log("Backtrack at stateID = " + search.getStateId() + ", depth = " + search.getDepth());
	}
	
	@Override
	public void stateRestored(Search search) {
		Logger.log("State restored");
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
//			try_seq_log(root, new ArrayList<Configuration<String>>(), new ArrayList<TestCase>());
			endGraph();
			Logger.log("Finished !!!");
		} catch (IOException e) {
		}
	}

	private void beginGraph() throws IOException {
		graph = new BufferedWriter(new FileWriter(out_filename));
	}

	private void endGraph() throws IOException {
		graph.close();
		Logger.log(PRINT_COUNT);
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
	
	private void showHeap(VM vm) {
		for (ElementInfo ei : vm.getHeap().liveObjects()) {
			String name = ei.getClassInfo().getName();
			Logger.log(name + "-" + ei.getObjectRef());
			showFieldInfos(ei);
		}
	}
	
	private Configuration<String> getConfiguration(Search search) {
		Configuration<String> config = new Configuration<String>();
		config.setStateId(search.getStateId());
		config.setDepth(search.getDepth());
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
					case "index":
						// index -> done
						int index = ei.getIntField(fi);
						config.setIndex(index);
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
		ElementInfo ei_elementData = (ElementInfo) ei_packetsReceived.getFieldValueObject("elementData");
		ArrayList<String> packetsReceived = new ArrayList<String>();
		if (ei_elementData != null) {
			ReferenceArrayFields raf = (ReferenceArrayFields) ei_elementData.getArrayFields();
			for (int i : (int[])raf.getValues()) {
				if (i > 0) {
					ElementInfo ei_rf = vm.getHeap().get(i);
					ElementInfo ei_value = (ElementInfo) ei_rf.getFieldValueObject("value");
					CharArrayFields caf = (CharArrayFields) ei_value.getArrayFields();
					packetsReceived.add(String.valueOf((char[])caf.getValues()));
				}
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
