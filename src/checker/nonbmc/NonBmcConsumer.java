package checker.nonbmc;

import org.apache.commons.lang3.SerializationUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Logger;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.DeliverCallback;

import checker.factory.ConsumerFactory;
import jpf.common.OC;
import mq.RabbitMQClient;
import mq.RunJPF;
import server.Application;
import server.ApplicationConfigurator;

public class NonBmcConsumer implements ConsumerFactory {

	protected static Logger logger = (Logger) LogManager.getLogger();
	
	public NonBmcConsumer() {
	};
	
	@Override
	public void start() {
		try {
			Application app = ApplicationConfigurator.getInstance().getApplication();
			RabbitMQClient rabbitClient = RabbitMQClient.getInstance();
			Channel channel = rabbitClient.getChannel();
			String queueName = app.getRabbitMQ().getQueueName();
			rabbitClient.queueDeclare(queueName);
			System.out.println(" [*] Waiting for messages. To exit press CTRL+C");
			DeliverCallback deliverCallback = (consumerTag, delivery) -> {
				OC config = SerializationUtils.deserialize(delivery.getBody());
				System.out.println(" [x] Received '" + config);
				RunJPF runner = new RunJPF(config);
				runner.start();
				try {
					runner.join();
					channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
					logger.info("Sending ack");
				} catch (InterruptedException e) {
					logger.error("waiting jpf and ack to rabbitmq " + e.getMessage());
					e.printStackTrace();
				}
			};
			rabbitClient.setDeliverCallBack(deliverCallback);
			rabbitClient.basicConsume(queueName);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}
