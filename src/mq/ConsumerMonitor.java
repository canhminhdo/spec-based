package mq;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class ConsumerMonitor extends Thread {
	
	protected static Logger logger = (Logger) LogManager.getLogger();
	private static int TIMEOUT = 5 * 60 * 1000;	// 5 minutes
	private Consumer consumer;

	public ConsumerMonitor(Consumer consumer) {
		this.consumer = consumer;
	}

	public void run() {
		try {
			while (true) {
				synchronized (Receiver.LOCK) {
					Receiver.LOCK.wait(TIMEOUT);
					consumer.checkToChangeQueueOrNot();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
