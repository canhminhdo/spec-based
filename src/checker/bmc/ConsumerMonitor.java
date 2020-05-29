package checker.bmc;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

public class ConsumerMonitor extends Thread {
	
	protected static Logger logger = (Logger) LogManager.getLogger();
	private static int TIMEOUT = 60 * 1000;	// 1 min
	private Consumer consumer;

	public ConsumerMonitor(Consumer consumer) {
		this.consumer = consumer;
	}

	public void run() {
		try {
			while (true) {
				synchronized (BmcConsumer.LOCK) {
					consumer.checkToChangeQueueOrNot();
					BmcConsumer.LOCK.wait(TIMEOUT);
				}
			}
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
