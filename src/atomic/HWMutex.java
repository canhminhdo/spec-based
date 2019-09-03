package atomic;

/**
 * Hadware Mutex Implementation
 * 
 * @author OgataLab
 *
 */
public class HWMutex implements Lock {
	
	private TestAndSet lockFlag;

	public HWMutex() {
	}

	public HWMutex(TestAndSet lockFlag) {
		this.lockFlag = lockFlag;
	}

	public TestAndSet getLockFlag() {
		return lockFlag;
	}

	public void setLockFlag(TestAndSet lockFlag) {
		this.lockFlag = lockFlag;
	}

	@Override
	public void requestCS() { // entry protocol
		while (lockFlag.testAndSet(1) == 1);
	}

	@Override
	public void releaseCS() { // exit protocol
		lockFlag.testAndSet(0);
	}
}
