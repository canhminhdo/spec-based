package mq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import config.Env;
import database.RedisClient;
import jpf.Configuration;
import main.Cell;
import main.Pair;
import server.Application;
import server.ApplicationConfigurator;
import server.Redis;
import server.ServerFactory;

public class Starter {
	
	public static void main(String[] argv) {
		try {
			// Initialize application with configuration
			Application app = ApplicationConfigurator.getInstance().getApplication();
			ServerFactory serverFactory = app.getServerFactory();
			Redis redis = app.getRedis();
			server.RabbitMQ rabbitMQ = app.getRabbitMQ();
			
			// Clean up before kicking off environment
			
			// Remove all old data in "maude" folder
			Process p1 = Runtime.getRuntime().exec(new String[] {"/bin/bash", "-c", "rm " + Env.MAUDE_DATA_PATH});
			// Purge queue
			Process p2 = null;
			if (!serverFactory.isRemote())
				p2 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name=" + rabbitMQ.getQueueName());
			
			// Flush all keys and values from redis server
			RedisClient.getInstance(redis.getHost(), redis.getPort()).getConnection().flushAll();
			
			// Wait
			p1.waitFor();
			if (!app.getServerFactory().isRemote())
				p2.waitFor();
			
			// Push a initial job to message queue 
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost(app.getRabbitMQ().getHost());
			if (serverFactory.isRemote()) {
				factory.setUsername(rabbitMQ.getUserName());
				factory.setPassword(rabbitMQ.getPassword());
			}
			
			try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
				channel.queueDeclare(rabbitMQ.getQueueName(), false, false, false, null);
	
				// prepare to send a message to queue
				Configuration<String> config = new Configuration<String>();
		        List<String> sentPackets = Arrays.asList(Env.PACKETS);
		        List<String> recPackets = new ArrayList<String>();
//		        String packetsRecived[] = { "0" };
//		        List<String> recPackets = Arrays.asList(packetsRecived);
				main.Channel<Pair<String,Boolean>> ch1 = new main.Channel<Pair<String,Boolean>>(Env.BOUND);
//				ch1.put(new Pair<String,Boolean>("0",false));
//				ch1.put(new Pair<String,Boolean>("2",true));
				main.Channel<Boolean> ch2 = new main.Channel<Boolean>(Env.BOUND);
//				ch2.put(true);
//				ch2.put(false);
				Cell<Boolean> f = new Cell<Boolean>(false);
				
				// 1st argument: packetsToBeSent
				config.setPacketsToBeSent(sentPackets);
				// 2nd argument: packetsReceived
		        config.setPacketsReceived(recPackets);
		        // 3rd argument: index of packetsToBeSent
		        config.setIndex(0);
		        // 4th argument: finish flag
		        config.setFinish(f);
		        // 5th argument: flag1
		        config.setFlag1(true);
		        // 6th argument: flag2
				config.setFlag2(true);
				// 7th argument: channel1
		        config.setChannel1(ch1);
		        // 8th argument: channel2
		        config.setChannel2(ch2);
		        
				byte[] data = SerializationUtils.serialize(config);
	
				channel.basicPublish("", rabbitMQ.getQueueName(), null, data);
				System.out.println(" [x] Sent '" + config);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
