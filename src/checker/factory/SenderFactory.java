package checker.factory;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import jpf.common.OC;
import mq.RabbitMQClient;
import server.Application;
import server.ApplicationConfigurator;

public abstract class SenderFactory {
	
	private static Logger logger = (Logger) LogManager.getLogger();
	protected Application app;
	protected RabbitMQClient rabbitClient;

	protected SenderFactory() {
		try {
			app = ApplicationConfigurator.getInstance().getApplication();
			rabbitClient = RabbitMQClient.getInstance();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

	public abstract void sendJob(OC message);

	public void sendMaudeJob(String seq) {
		try {
			// String cipher = AES.encrypt(seq, CaseStudy.SECRETE_KEY); // Encrypt before sending
			rabbitClient.basicPublish(app.getRabbitMQ().getMaudeQueue(), SerializationUtils.serialize(seq));
			logger.debug(" [x] Sent to Maude '" + seq);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	public void sendJob(String queueName, OC message) {
		try {
			rabbitClient.basicPublish(queueName, SerializationUtils.serialize(message));
			logger.debug(" [x] Sent '" + message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public abstract void close();
	
	public abstract void queueDeclare();

}
