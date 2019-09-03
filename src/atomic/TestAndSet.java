package atomic;

import config.CaseStudy;
import gov.nasa.jpf.vm.Verify;

/**
 * TestAndSet file supports Test&Set protocol
 * 
 * @author OgataLab
 *
 */
public class TestAndSet {
	Integer myValue = 0;

	public TestAndSet() {
	}
	
	/**
	 * Synchronized test and test function
	 * 
	 * @param newValue
	 * @return {@link Integer}
	 */
	public synchronized int testAndSet(int newValue) {
		if (CaseStudy.JPF_MODE) Verify.beginAtomic();
		int oldValue = myValue;
		myValue = newValue;
		if (CaseStudy.JPF_MODE) Verify.endAtomic();
		return oldValue;
	}
}
