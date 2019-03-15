package jpf;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

import gov.nasa.jpf.Config;
import gov.nasa.jpf.JPF;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.search.Search;
import gov.nasa.jpf.vm.ChoiceGenerator;
import gov.nasa.jpf.vm.ElementInfo;
import gov.nasa.jpf.vm.FieldInfo;
import gov.nasa.jpf.vm.Heap;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.NamedFields;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.VM;

public class SequenceState extends ListenerAdapter {
	
	private static final int DEPTH = 13;
	private static final String TXT_EXT = "dot";
	private static final String OUT_FILENAME_NO_EXT = "jpf-sequence-state";

	private BufferedWriter graph;
	private String out_filename = OUT_FILENAME_NO_EXT + "." + TXT_EXT;
	private PrintStream out;
	
	private static int STARTUP = 1;
	private Map<String,Integer> lookupTable = new HashMap<String,Integer>();
	
	private static final String[] observers = {"channel1","channel2","packetsToBeSent","packetsReceived","finish","flag1","flag2"};

	public SequenceState(Config conf, JPF jpf) {
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
		if (SequenceState.STARTUP == 1) {
			startup(search.getVM());
		}
		SequenceState.STARTUP ++;
		Logger.log("StateID: " + search.getStateId());
		System.exit(0);
		search.terminate();
//		
//		if (search.isNewState()) {
//			VM vm = search.getVM();
//			Heap heap = vm.getHeap();
//			Iterable<ElementInfo> iterElementInfo = heap.liveObjects();
//			for (ElementInfo ei : iterElementInfo) {
//				// ElementInfo describes an element of memory containing the field values of a class or an object
//				String name = ei.getClassInfo().getName();
//				Logger.log(name);
//				if (name.equals("Receiver")) {
//					Logger.log("---------------");
//					Logger.log("real: " + name);
//					Logger.log("lookup: " + lookupTable.get(ei.getObjectRef()));
//					FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
//					for (FieldInfo fi : fis) {
//						// FieldInfo contains type, name and attribute information of a field
//						if (fi.getName().equals("p")) {
//							if (fi.isReference()) {
//								ElementInfo he = (ElementInfo) fi.getValueObject(ei.getFields());
//								Logger.log("canhdominh: " + he.getObjectRef());
//								if (he != null) {
//									FieldInfo[] _fis = he.getClassInfo().getInstanceFields();
//									for (FieldInfo _fi : _fis) {
//										Logger.log(_fi.getName() + _fi.getType());
//										if (_fi.getName().equals("z")) {
//											ElementInfo te = (ElementInfo) _fi.getValueObject(he.getFields());
//											if (te != null) {
//												IntArrayFields z = (IntArrayFields) te.getArrayFields();
//												int[] _z = z.asIntArray();
//												for (int __z : _z) {
//													Logger.log(__z);
//												}
//											}
//										}
//									}
//									// Get value from x and y variables
//									Logger.log(he.getStringField("x"));
//									Logger.log(he.getStringField("y"));
//								}
//							}
//						}
//					}
//				}
//			}
//			
////			out.print(search.getStateId());
////			Logger.log("stateAdvanced");
//			int depth = search.getDepth();
//			if (depth > DEPTH)
//				search.terminate();
//		} else {
//			search.terminate();
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
		Logger.log("");
		for (ElementInfo ei : vm.getHeap().liveObjects()) {
			String name = ei.getClassInfo().getName();
			if (name.contains("Sender") || name.contains("Receiver")) {
				// Sender
				lookupTable.put(name, ei.getObjectRef());
				FieldInfo[] fis = ei.getClassInfo().getInstanceFields();
				for (FieldInfo fi : fis) {
					
//					if (fi.getName().equals("flag1")) {
//						if (fi.isReference()) {
//							ElementInfo _ei = (ElementInfo)fi.getValueObject(ei.getFields());
//							// tricky here: please ask me to know more detail :D:D:D
//							NamedFields nf = (NamedFields)_ei.getFields();
//							Logger.log(nf.getBooleanValue(0));
//						}
//					}
					
					if (Arrays.stream(observers).anyMatch(fi.getName()::equals)) {
						Logger.log(fi.getName() + "-" + fi.getType());
						putToLookupTable(ei, fi);
					}
				}
			}
		}
		showLookupTable();
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
