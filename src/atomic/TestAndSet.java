package atomic;

import config.Env;
import gov.nasa.jpf.vm.Verify;

public class TestAndSet {
	Integer myValue = 0;

	public TestAndSet() {
	}

	public synchronized int testAndSet(int newValue) {
		if (Env.JPF_MODE) Verify.beginAtomic();
		int oldValue = myValue;
		myValue = newValue;
		if (Env.JPF_MODE) Verify.endAtomic();
		return oldValue;
	}
}
