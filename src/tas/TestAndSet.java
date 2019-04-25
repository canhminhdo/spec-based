package tas;

public class TestAndSet {
	int myValue = 0;

	public synchronized int testAndSet(int newValue) {
		int oldValue = myValue;
		myValue = newValue;
		return oldValue;
	}
}
