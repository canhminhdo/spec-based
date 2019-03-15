package jpf;

public class ArrayUtils {
	public static void main(String[] args) {
		// Avoid NullPointerException -> reducing null check
		int [] listeners = new int[0];
		// NullPointerException will occur
		int [] test_listeners = null;
		
		for (int x : listeners) {
			System.out.println(x);
		}
		
		for (int y : test_listeners) {
			System.out.println(y);
		}
	}
}
