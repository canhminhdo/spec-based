package redis.api;

import java.util.ArrayList;
import java.util.Map;

import application.model.ConsumerInfo;

public class RedisConsumerInfo extends RedisHash {

	public static String CONSUMER_INFO_KEY = "consumer-info";

	public RedisConsumerInfo() {
		super();
	}

	public ArrayList<ConsumerInfo> getConsumerInfo() {
		ArrayList<ConsumerInfo> consumerList = new ArrayList<ConsumerInfo>();
		Map<String, String> consumersInfo = this.hgetall(CONSUMER_INFO_KEY);
		for (Map.Entry<String, String> entry : consumersInfo.entrySet()) {
			ConsumerInfo consumer = new ConsumerInfo(Integer.parseInt(entry.getKey()), Integer.parseInt(entry.getValue()));
			consumerList.add(consumer);
		}
		return consumerList;
	}
	
	public ConsumerInfo getConsumerInfoById(Integer id) {
		String current = this.hget(CONSUMER_INFO_KEY, id.toString());
		if (current.equals(""))
			return null;
		return new ConsumerInfo(id, Integer.parseInt(current));
	}
	
	public int getNumberOfConsumer() {
		return hlen(CONSUMER_INFO_KEY);
	}
	
	public void updateConsumerInfo(ConsumerInfo consumer) {
		this.hset(CONSUMER_INFO_KEY, String.valueOf(consumer.getId()), String.valueOf(consumer.getCurrent()));
	}
	
	public void clearWorkers() {
		this.deleteKey(CONSUMER_INFO_KEY);
	}
}
