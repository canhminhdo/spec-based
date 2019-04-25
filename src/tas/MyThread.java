package tas;

import java.util.Random;

public class MyThread extends Thread {
	private int myId;
	private Lock lock;
	Random r = new Random();
	
	public MyThread(int id, Lock lock) {
		this.myId = id;
		this.lock = lock;
	}
	
	public void rs() {
        try {
        	System.out.println(myId + " is not in CS");
			sleep(r.nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void cs() {
		try {
        	System.out.println(myId + " is in CS *****");
			sleep(r.nextInt(1000));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	public void run() {
        while (true) {
            lock.requestCS();
            cs();
            lock.releaseCS();
            rs();
        }
    }
}
