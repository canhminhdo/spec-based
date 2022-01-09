package checker.bmc;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import checker.factory.SenderFactory;
import config.CaseStudy;
import jpf.common.OC;

public class BmcSender extends SenderFactory {
	
	private static Logger logger = (Logger) LogManager.getLogger();
	private static BmcSender _instance;
	private final int SIZE = 3;
	
	private BmcSender() {
		super();
		queueDeclare();
	}
	
	public static BmcSender getInstance() {
		if (_instance == null)
			_instance = new BmcSender();
		return _instance;
	}
	
	@Override
	public void queueDeclare() {
		try {
			for (int i = 0; i < SIZE; i ++) {
				rabbitClient.queueDeclare(app.getRabbitMQ().getQueueName() + i);
			}
			if (CaseStudy.MAUDE_WORKER_IS_ENABLE)
				rabbitClient.queueDeclare(app.getRabbitMQ().getMaudeQueue());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public String getQueueServe() {
		return app.getRabbitMQ().getQueueName() + ((Consumer.current + 1) % SIZE);
	}
	
	@Override
	public void sendJob(OC message) {
		try {
			rabbitClient.basicPublish(getQueueServe(), SerializationUtils.serialize(message));
			System.out.println(" [x] Sent '" + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void close() {
		try {
			rabbitClient.close();
			_instance = null;
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
