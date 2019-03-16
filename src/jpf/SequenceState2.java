package jpf;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.CharArrayFields;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.Heap;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.NamedFields;
import gov.nasa.jpf.vm.ReferenceArrayFields;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.VM;
import main.Cell;
import main.Channel;
import main.Pair;

public class SequenceState2 extends ListenerAdapter {
	
	private static final int DEPTH = 20;
	private static final String TXT_EXT = "dot";
	private static final String OUT_FILENAME_NO_EXT = "jpf-sequence-state";

	private BufferedWriter graph;
	private String out_filename = OUT_FILENAME_NO_EXT + "." + TXT_EXT;
	private PrintStream out;
	
	private static int STARTUP = 1;
	private Map<String,Integer> lookupTable = new HashMap<String,Integer>();
	
	private static final String[] observers = {"channel1","channel2","packetsToBeSent","packetsReceived","finish","flag1","flag2"};

	public SequenceState2(Config conf, JPF jpf) {
		VM vm = jpf.getVM();
		vm.recordSteps(true);
		out = System.out;
	}

	/**
	 * VM is about to execute the next instruction
	 */
	@Override
	public void executeInstruction(VM vm, ThreadInfo currentThread, Instruction instructionToExecute) {
//		Logger.log("executeInstruction");
		out.print(">");
//		Logger.log(instructionToExecute.getMnemonic());
	}

	/**
	 * VM has executed the next instruction
	 */
	@Override
	public void instructionExecuted(VM vm, ThreadInfo currentThread, Instruction nextInstruction,
			Instruction executedInstruction) {
//		Logger.log("executedInstruction");
		out.print("<" + vm.getSearch().getStateId());
	}

	/**
	 * a new ChoiceGenerator was set, which means we are at the beginning of a new
	 * transition.
	 *
	 * NOTE - this notification happens before the KernelState is stored, i.e.
	 * listeners are NOT allowed to alter the KernelState (e.g. by changing field
	 * values or thread states)
	 */
	@Override
	public void choiceGeneratorSet(VM vm, ChoiceGenerator<?> newCG) {
		Logger.log("choiceGeneratorSet");
		Object[] all = newCG.getAllChoices();
		for(Object e : all) {
			Logger.log(e);
		}
	}

	/**
	 * the next choice was requested from a previously registered ChoiceGenerator
	 *
	 * NOTE - this notification happens before the KernelState is stored, i.e.
	 * listeners are NOT allowed to alter the KernelState (e.g. by changing field
	 * values or thread states)
	 */
	@Override
	public void choiceGeneratorAdvanced(VM vm, ChoiceGenerator<?> currentCG) {
//		Logger.log("choiceGeneratorAdvanced");
	}

	/**
	 * got the next state
	 * 
	 * Note - this will be notified before any potential propertyViolated, in which
	 * case the currentError will be already set
	 */
	@Override
	public void stateAdvanced(Search search) {
		if (SequenceState2.STARTUP == 1) {
			SequenceState2.STARTUP ++;
			startup(search.getVM());
		}
		getConfiguration(search);
		System.exit(0);
//		if (search.isNewState()) {
//			VM vm = search.getVM();
//			Heap heap = vm.getHeap();
//			int depth = search.getDepth();
//			if (depth > DEPTH)
//				search.terminate();
//		} else {
//			Logger.log("TESTING");
//		}
	}

	@Override
	public void stateProcessed(Search search) {
		// nothing to do
	}

	@Override
	public void stateBacktracked(Search search) {
		search.getVM().breakTransition("DONE");
//		Logger.log("stateBacktracked");
//		int depth = search.getDepth();
//		Logger.log(depth);
	}

	@Override
	public void stateRestored(Search search) {
//		Logger.log("stateRestored");
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
			endGraph();
		} catch (IOException e) {
		}
	}

	private void beginGraph() throws IOException {
		graph = new BufferedWriter(new FileWriter(out_filename));
		graph.write("digraph jpf_sequence_state {");
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
	
	private void printHeap(VM vm) {
		Heap heap = vm.getHeap();
		Iterable<ElementInfo> iterElementInfo = heap.liveObjects();
		for (ElementInfo ei : iterElementInfo) {
			// ElementInfo describes an element of memory containing the field values of a class or an object
			Logger.log(ei.getObjectRef() + " - " + ei.getClassInfo().getName());
		}
	}
	
	private void startup(VM vm) {
		for (ElementInfo ei : vm.getHeap().liveObjects()) {
			String name = ei.getClassInfo().getName();
			if (name.contains("Sender") || name.contains("Receiver")) {
				lookupTable.put(name, ei.getObjectRef());
//				FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
//				for (FieldInfo fi : fis) {
//					
//					// flag1 -> done
//					if (fi.getName().equals("flag1")) {
//						if (fi.isReference()) {
//							ElementInfo ei_flag1 = (ElementInfo)fi.getValueObject(ei.getFields());
//							Boolean flag1 = getFlag(ei_flag1);
//							Logger.log(flag1);
//						}
//					}
//					
//					// flag2 -> done
//					if (fi.getName().equals("flag2")) {
//						if (fi.isReference()) {
//							ElementInfo ei_flag2 = (ElementInfo)fi.getValueObject(ei.getFields());
//							Boolean flag2 = getFlag(ei_flag2);
//							Logger.log(flag2);
//						}
//					}
//					
//					// finish -> done
//					if (fi.getName().equals("finish")) {
//						if (fi.isReference()) {
//							ElementInfo ei_finish = (ElementInfo)fi.getValueObject(ei.getFields());
//							Cell<Boolean> finish = getFinish(ei_finish);
//							Logger.log(finish);
//						}
//					}
//					
//					// packetsToBeSent -> done
//					if (fi.getName().equals("packetsToBeSent")) {
//						if (fi.isReference()) {
//							ElementInfo ei_packetsToBeSent = (ElementInfo)fi.getValueObject(ei.getFields());
//							ArrayList<String> sentPackets = getPacketsToBeSent(vm, ei_packetsToBeSent);
//							Logger.log(sentPackets);
//						}
//					}
//					
//					// packetsReceived -> done
//					if (fi.getName().equals("packetsReceived")) {
//						if (fi.isReference()) {
//							ElementInfo ei_packetsReceived = (ElementInfo)fi.getValueObject(ei.getFields());
//							ArrayList<String> recPackets = getPacketsReceived(vm, ei_packetsReceived);
//							Logger.log(recPackets);
//						}
//					}
//					
//					// channel2 -> done
//					if (fi.getName().equals("channel2")) {
//						if (fi.isReference()) {
//							ElementInfo ei_channel2 = (ElementInfo)fi.getValueObject(ei.getFields());
//							// int nop = ei_channel2.getIntField("nop");
//							int bound = ei_channel2.getIntField("bound");
//							ElementInfo ei_queue = (ElementInfo) ei_channel2.getFieldValueObject("queue");
//							Channel<Boolean> ch2 = getChannelBoolean(ei_queue, bound);
//							Logger.log(ch2.toString());
//						}
//					}
//					
//					// channel 1 -> done
//					if (fi.getName().equals("channel1")) {
//						if (fi.isReference()) {
//							ElementInfo ei_channel1 = (ElementInfo)fi.getValueObject(ei.getFields());
//							// int nop = ei_channel1.getIntField("nop");
//							int bound = ei_channel1.getIntField("bound");
//							ElementInfo ei_queue = (ElementInfo) ei_channel1.getFieldValueObject("queue");
//							Channel<Pair<String,Boolean>> ch1 = getChannelPair(ei_queue, bound);
//							Logger.log(ch1.toString());
//						}
//					}
//					
//					if (Arrays.stream(observers).anyMatch(fi.getName()::equals)) {
//						putToLookupTable(ei, fi);
//					}
//				}
			}
		}
		showLookupTable();
	}
	
	private void getConfiguration(Search search) {
		Configuration<String> config = new Configuration<String>();
		config.setStateId(search.getStateId());
		config.setDeep(search.getDepth());
		Heap heap = search.getVM().getHeap();
		{
			// Sender
			ElementInfo ei = heap.get(lookupTable.get("main.Sender"));
			FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
			for (FieldInfo fi : fis) {
				switch (fi.getName()) {
					case "flag1":
						// flag1 -> done
						ElementInfo ei_flag1 = (ElementInfo)fi.getValueObject(ei.getFields());
						Boolean flag1 = getFlag(ei_flag1);
						config.setFlag1(flag1);
//						Logger.log("flag1 = " + flag1);
						break;
					case "finish":
						// finish -> done
						ElementInfo ei_finish = (ElementInfo)fi.getValueObject(ei.getFields());
						Cell<Boolean> finish = getFinish(ei_finish);
						config.setFinish(finish);
//						Logger.log("finish = " + finish);
						break;
					case "packetsToBeSent":
						// packetsToBeSent -> done
						ElementInfo ei_packetsToBeSent = (ElementInfo)fi.getValueObject(ei.getFields());
						ArrayList<String> packetsToBeSent = getPacketsToBeSent(search.getVM(), ei_packetsToBeSent);
						config.setPacketsToBeSent(packetsToBeSent);
//						Logger.log("packetsToBeSent = " + sentPackets);
					case "channel2":
						// channel2 -> done
						ElementInfo ei_channel2 = (ElementInfo)fi.getValueObject(ei.getFields());
						if (ei_channel2.getClassInfo().getInstanceField("bound") == null) {
							Logger.log("channel2 = empty");
						} else {
							// int nop = ei_channel2.getIntField("nop");
							int bound = ei_channel2.getIntField("bound");
							ElementInfo ei_queue = (ElementInfo) ei_channel2.getFieldValueObject("queue");
							Channel<Boolean> channel2 = getChannelBoolean(ei_queue, bound);
							config.setChannel2(channel2);
//							Logger.log("channel2 = " + channel2.toString());
						}
						break;
					case "channel1":
						// channel 1 -> done
						ElementInfo ei_channel1 = (ElementInfo)fi.getValueObject(ei.getFields());
						if (ei_channel1.getClassInfo().getInstanceField("bound") == null) {
							Logger.log("channel1 = empty");
						} else {
							// int nop = ei_channel1.getIntField("nop");
							int bound = ei_channel1.getIntField("bound");
							ElementInfo ei_queue = (ElementInfo) ei_channel1.getFieldValueObject("queue");
							Channel<Pair<String,Boolean>> channel1 = getChannelPair(ei_queue, bound);
							config.setChannel1(channel1);
//							Logger.log("channel1 = " + channel1.toString());
						}
					default:
						break;
				}
			}
		}
		{
			// Receiver
			ElementInfo ei = heap.get(lookupTable.get("main.Receiver"));
			FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
			for (FieldInfo fi : fis) {
				switch (fi.getName()) {
					case "flag2":
						// flag2 -> done
						ElementInfo ei_flag2 = (ElementInfo)fi.getValueObject(ei.getFields());
						Boolean flag2 = getFlag(ei_flag2);
						config.setFlag2(flag2);
//						Logger.log("flag2 = " + flag2);
						break;
					
					case "packetsReceived":
						// packetsReceived -> done
						ElementInfo ei_packetsReceived = (ElementInfo)fi.getValueObject(ei.getFields());
						ArrayList<String> packetsReceived = getPacketsReceived(search.getVM(), ei_packetsReceived);
						config.setPacketsReceived(packetsReceived);
						Logger.log("packetsReceived = " + packetsReceived);
					default:
						break;
				}
			}
		}
		Logger.log(config);
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
	
	private void putToLookupTable(ElementInfo ei, FieldInfo fi) {
		if (fi.isReference()) {
			ElementInfo _ei = (ElementInfo) fi.getValueObject(ei.getFields());
			if (_ei != null)
				if (!lookupTable.containsKey(fi.getName()))
					lookupTable.put(fi.getName(),_ei.getObjectRef());
		}
	}
}
