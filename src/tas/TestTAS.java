package tas;

public class TestTAS {
	public static int N = 3;

	public static void main(String[] args) {
		TestAndSet lockFlag = new TestAndSet();
		Lock lock = new HWMutex(lockFlag);
		MyThread t[] = new MyThread[N];

		for (int i = 0; i < N; i++) {
			t[i] = new MyThread(i, lock);
		}

		for (int i = 0; i < N; i++) {
			t[i].start();
		}
	}
}
