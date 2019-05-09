package mq;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.SerializationUtils;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import database.RedisClient;
import jpf.Configuration;
import main.Cell;
import main.Pair;

public class Starter implements RabbitMQ {
	
	public static void main(String[] argv) {
		try {
			// Remove all old data in "maude" folder
			Process p1 = Runtime.getRuntime().exec(new String[] {"/bin/bash", "-c", "rm /Users/student/eclipse-workspace/abp/maude/data*"});
			// Purge queue
			Process p2 = Runtime.getRuntime().exec("/usr/local/opt/rabbitmq/sbin/rabbitmqadmin purge queue name=" + QUEUE_NAME);
			
			// Flush all keys and values from redis server
			RedisClient.getInstance().getConnection().flushAll();
			
			// Wait
			p1.waitFor();
			p2.waitFor();
			
			// Push a initial job to message queue 
			ConnectionFactory factory = new ConnectionFactory();
			factory.setHost("localhost");
			try (Connection connection = factory.newConnection(); Channel channel = connection.createChannel()) {
				channel.queueDeclare(QUEUE_NAME, false, false, false, null);
	
				// prepare to send a message to queue
				Configuration<String> config = new Configuration<String>();
				int bound = 3;
				String packets[] = { "0", "1", "2" };
		        List<String> sentPackets = Arrays.asList(packets);
		        List<String> recPackets = new ArrayList<String>();
//		        List<String> recPackets = Arrays.asList(packets);
				main.Channel<Pair<String,Boolean>> ch1 = new main.Channel<Pair<String,Boolean>>(bound);
//				ch1.put(new Pair<String,Boolean>("1",true));
//				ch1.put(new Pair<String,Boolean>("2",true));
				main.Channel<Boolean> ch2 = new main.Channel<Boolean>(bound);
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
		        config.setFlag1(false);
		        // 6th argument: flag2
				config.setFlag2(false);
				// 7th argument: channel1
		        config.setChannel1(ch1);
		        // 8th argument: channel2
		        config.setChannel2(ch2);
		        
				byte[] data = SerializationUtils.serialize(config);
	
				channel.basicPublish("", QUEUE_NAME, null, data);
				System.out.println(" [x] Sent '" + config);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
