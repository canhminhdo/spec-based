package jpf;
import gov.nasa.jpf.ListenerAdapter;
import gov.nasa.jpf.vm.Instruction;
import gov.nasa.jpf.vm.ThreadInfo;
import gov.nasa.jpf.vm.VM;

public class OpCodePrinter extends ListenerAdapter {
	String lastLoc = "";

	@Override
	public void threadStarted(VM vm, ThreadInfo startedThread) {
		// TODO Auto-generated method stub
		super.threadStarted(vm, startedThread);
		System.out.println("Do Minh Canh");
	}

	@Override
	public void instructionExecuted(VM vm, ThreadInfo currentThread, Instruction nextInstruction,
			Instruction executedInstruction) {
		// TODO Auto-generated method stub
		super.instructionExecuted(vm, currentThread, nextInstruction, executedInstruction);
		System.out.println("Do Minh Canh");
	}

	
}
