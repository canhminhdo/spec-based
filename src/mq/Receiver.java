package mq;

/**
 * Receiver program as RabbitMQ client Whenever receiving a message from
 * RabbitMQ master Start internally JPF program to generate state sequences
 * 
 * @author OgataLab
 *
 */
public class Receiver {
	
	public static Object LOCK = new Object();
	/**
	 * Starting a RabbitMQ client.
	 * 
	 * @param argv Unsed.
	 * @throws Exception
	 */
	public static void main(String[] args) throws Exception {
		Consumer consumer = new Consumer();
		consumer.handle();
		ConsumerMonitor monitor = new ConsumerMonitor(consumer);
		monitor.start();
		monitor.join();
	}
}
