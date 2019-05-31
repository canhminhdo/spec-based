package mq;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import jpf.Configuration;
import server.Application;
import server.ApplicationConfigurator;
import server.ServerFactory;

public class Receiver {

    public static void main(String[] argv) throws Exception {
    	// Initialize application with configuration
		Application app = ApplicationConfigurator.getInstance().getApplication();
		ServerFactory serverFactory = app.getServerFactory();
		server.RabbitMQ rabbitMQ = app.getRabbitMQ();
    	
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost(rabbitMQ.getHost());
        if (serverFactory.isRemote()) {
			factory.setUsername(rabbitMQ.getUserName());
			factory.setPassword(rabbitMQ.getPassword());
		}
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        channel.queueDeclare(rabbitMQ.getQueueName(), false, false, false, null);
        System.out.println(" [*] Waiting for messages. To exit press CTRL+C");

        DeliverCallback deliverCallback = (consumerTag, delivery) -> {
        	Configuration<String> config = SerializationUtils.deserialize(delivery.getBody());
            System.out.println(" [x] Received '" + config);
            RunJPF runner = new RunJPF(config);
            runner.start();
            try {
				runner.join();
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
        };
        boolean autoAck = false;
        channel.basicConsume(rabbitMQ.getQueueName(), autoAck, deliverCallback, consumerTag -> { });
    }

}
