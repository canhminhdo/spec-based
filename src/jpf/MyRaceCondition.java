package jpf;

public class MyRaceCondition {
	private Boolean bit;
	
	private static class Pair {
		String x = "x";
		String y = "y";
		int[] z = {1, 2, 3};
		
		public void update() {
			x = x + y + x;
		}
		
		public String toString() {
			return x + y;
		}
	}
	
	private static class RC extends Thread {
		Pair p;
		
		public void run() {
			p.update();
		}
	}
	
	public static void main(String[] args) {
		Pair p = new Pair();
		RC rc1 = new RC();
		RC rc2 = new RC();
		
		rc1.p = p;
		rc2.p = p;
		
		rc1.start();
		rc2.start();
		try {
			rc1.join();
			rc2.join();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
//		System.out.println("x: " + p.x);
	}
}
