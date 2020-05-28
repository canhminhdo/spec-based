package checker.nonbmc;

import java.io.IOException;

import org.apache.commons.lang3.SerializationUtils;

import checker.factory.SenderFactory;
import config.CaseStudy;
import jpf.common.OC;

public class NonBmcSender extends SenderFactory {
	
	private static NonBmcSender _instance;
	
	private NonBmcSender() {
		super();
		queueDeclare();
	}
	
	public static NonBmcSender getInstance() {
		if (_instance == null) {
			_instance = new NonBmcSender();
		}
		return _instance;
	}
	
	@Override
	public void queueDeclare() {
		try {
			rabbitClient.queueDeclare(app.getRabbitMQ().getQueueName());
			if (CaseStudy.MAUDE_WORKER_IS_ENABLE)
				rabbitClient.queueDeclare(app.getRabbitMQ().getMaudeQueue());
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void sendJob(OC message) {
		try {
			rabbitClient.basicPublish(app.getRabbitMQ().getQueueName(), SerializationUtils.serialize(message));
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
