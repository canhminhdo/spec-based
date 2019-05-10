package jpf;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import database.RedisClient;
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
import redis.clients.jedis.Jedis;
import utils.DateUtil;
import utils.GFG;

public class SequenceState extends ListenerAdapter {
	
	private final int DEPTH = 50;
	private final int BOUND = 1000;
	private boolean DEPTH_FLAG = true;
	private boolean BOUND_FLAG = false;
	private int COUNT = 0;
	private int SEQ_UNIQUE_COUNT = 0;
	private final String TXT_EXT = "txt";
	private final String OUT_FILENAME_NO_EXT = "./maude/data";

	private BufferedWriter graph;
	private String out_filename = OUT_FILENAME_NO_EXT + "-" + DateUtil.getTime() + "." + TXT_EXT;
	
	private int STARTUP = 1;
	private Map<String,Integer> lookupTable;
	private ArrayList<Configuration<String>> seq;
	private Jedis jedis = null;
	
	public SequenceState(Config conf, JPF jpf) {
		initialize();
		jedis.flushAll();
	}
	
	public SequenceState() {
		initialize();
	}
	
	private void initialize() {
		jedis = RedisClient.getInstance().getConnection();
		lookupTable = new HashMap<String,Integer>();
		seq = new ArrayList<Configuration<String>>();
	}
	
	public String seqToString() {
		if (seq.size() == 0) {
			return "nil";
		}
		Configuration<String> config = seq.get(0);
		StringBuffer sb = new StringBuffer();
		sb.append("(");
		sb.append(config);
		for (int i = 1; i < seq.size(); i ++) {
			if (config.equals(seq.get(i))) {
//				Logger.log("Dupplicated");
				continue;
			}
			config = seq.get(i);
			sb.append(" | ");
			sb.append(config);
		}
		sb.append(" | nil)");
		return sb.toString();
	}
	
	public void writeSeqStringToFile() {
		try {
			if (seq.size() > 0) {
				String seqString = seqToString();
				String seqSha256 = GFG.getSHA(seqString);
				if (!jedis.exists(seqSha256)) {
					jedis.set(seqSha256, seqString);
					graph.write(seqString + " , ");
					graph.newLine();
					SEQ_UNIQUE_COUNT ++;
				}
				Configuration<String> lastElement = seq.get(seq.size() - 1);
				if (lastElement != null) {
					String elementSha256 = GFG.getSHA(lastElement.toString());
					if (!jedis.exists(elementSha256)) {
						jedis.set(elementSha256, lastElement.toString());
						// TODO :: submit job to the queue broker
						if (lastElement.getFinish().get() == false || lastElement.getIndex() < 2) {
							mq.Sender.getInstance().sendJob(lastElement);
						}
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
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
		if (STARTUP == 1) {
			STARTUP ++;
			startup(search.getVM());
		}
		Configuration<String> config = getConfiguration(search);
		if (config == null) {
			// Finish program
			search.requestBacktrack();
			Logger.log("Finish program at " + search.getDepth());
			COUNT ++;
			writeSeqStringToFile();
		} else {
			seq.add(config);
			if (search.isEndState() || !search.isNewState()) {
				// End state or is not new state (visited state). JPF will back track automatically
				COUNT ++;
				writeSeqStringToFile();
			} if (DEPTH_FLAG && search.getDepth() >= DEPTH) {
				// current depth is greater than DEPTH, back track
//				Logger.log("Reach to the bound depth " + search.getDepth());
				search.requestBacktrack();
				COUNT ++;
				writeSeqStringToFile();
			}
		}
		// 1413243
		if (BOUND_FLAG && COUNT >= BOUND) {
			// terminate when number of sequence of states reach to BOUND
			search.terminate();
		}
	}

	@Override
	public void stateBacktracked(Search search) {
		while (seq.size() > 0 && seq.get(seq.size() - 1).getStateId() != search.getStateId()) {
			seq.remove(seq.size() - 1);
		}
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
		Logger.log(COUNT + " - " + SEQ_UNIQUE_COUNT);
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
