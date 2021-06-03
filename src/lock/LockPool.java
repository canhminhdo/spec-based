package lock;

public abstract class LockPool extends Thread {
	Lock lock;
	Lock lock1;
	Lock lock2;
	
	public void setLock(Lock lock) {
		this.lock = lock;
	}
	
	public void setLock(Lock lock1, Lock lock2) {
		this.lock1 = lock1;
		this.lock2 = lock2;
	}
	
	public Lock getLock() {
		return lock;
	}
	
	public Lock getLock1() {
		return lock1;
	}
	
	public Lock getLock2() {
		return lock2;
	}
}
