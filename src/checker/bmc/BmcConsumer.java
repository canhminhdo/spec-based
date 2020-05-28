package checker.bmc;

import checker.factory.ConsumerFactory;

public class BmcConsumer implements ConsumerFactory {
	
	public static Object LOCK = new Object();
	
	@Override
	public void start() {
		try {
			Consumer consumer = new Consumer();
			consumer.handle();
			ConsumerMonitor monitor = new ConsumerMonitor(consumer);
			monitor.start();
			monitor.join();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}
