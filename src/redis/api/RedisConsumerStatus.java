package redis.api;

import java.util.ArrayList;
import java.util.Map;

import application.model.ConsumerStatus;

public class RedisConsumerStatus extends RedisHash {

	public static String CONSUMER_STATUS_KEY = "consumer-status";

	public RedisConsumerStatus() {
		super();
	}

	public ArrayList<ConsumerStatus> getConsumerStatus() {
		ArrayList<ConsumerStatus> consumerList = new ArrayList<ConsumerStatus>();
		Map<String, String> consumersInfo = this.hgetall(CONSUMER_STATUS_KEY);
		for (Map.Entry<String, String> entry : consumersInfo.entrySet()) {
			ConsumerStatus consumer = new ConsumerStatus(entry.getKey(), entry.getValue());
			consumerList.add(consumer);
		}
		return consumerList;
	}
	
	public ConsumerStatus getConsumerStatusById(String id) {
		String status = this.hget(CONSUMER_STATUS_KEY, id);
		return new ConsumerStatus(id, status);
	}
	
	public int getNumberOfConsumer() {
		return hlen(CONSUMER_STATUS_KEY);
	}
	
	public void updateConsumerStatus(ConsumerStatus consumer) {
		this.hset(CONSUMER_STATUS_KEY, String.valueOf(consumer.getId()), consumer.getStatus());
	}
}
