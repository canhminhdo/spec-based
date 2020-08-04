package checker.bmc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class ConsumerMonitor extends Thread {
	
	protected static Logger logger = (Logger) LogManager.getLogger();
	private static int TIMEOUT = 2 * 60 * 1000;	// 2 mins
	private Consumer consumer;

	public ConsumerMonitor(Consumer consumer) {
		this.consumer = consumer;
	}

	public void run() {
		try {
			while (true) {
				synchronized (BmcConsumer.LOCK) {
					BmcConsumer.LOCK.wait(TIMEOUT);
					consumer.checkToChangeQueueOrNot();
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
