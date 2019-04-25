package tas;

public class HWMutex implements Lock {
	private TestAndSet lockFlag;
	
	public HWMutex(TestAndSet lockFlag) {
		this.lockFlag = lockFlag;
	}

	@Override
	public void requestCS() {	// entry protocol
		while(lockFlag.testAndSet(1) == 1);
	}

	@Override
	public void releaseCS() {	// exit protocol
		lockFlag.testAndSet(0);
	}

}
