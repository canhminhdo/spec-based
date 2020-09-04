package redis.api;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import jpf.common.OC;
import utils.SerializationUtilsExt;

public class DumpData {
	
	public static void main(String[] argv) {
		int currentDepth = 200;
		String fileName = "analyze/state_sequences.txt";
		RedisStoreStates jedisStore = new RedisStoreStates();
		
		Map<String, String> states = jedisStore.hgetall(jedisStore.getStoreNameAtDepth(currentDepth));
		try {
			BufferedWriter writer = new BufferedWriter(new FileWriter(fileName, false));
			for (Map.Entry<String,String> state : states.entrySet()) {
				OC message = SerializationUtilsExt.deserialize(state.getValue());
				writer.append(message.toString());
				writer.newLine();
			}
			writer.close();
			System.out.println("Write to the `" + fileName + "` file successfully!!!");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		Map<String, String> error_states = jedisStore.hgetall(jedisStore.getStoreErrorNameAtDepth(currentDepth));
		for (Map.Entry<String,String> state : error_states.entrySet()) {
			OC message = SerializationUtilsExt.deserialize(state.getValue());
			System.out.println(message.getCurrentDepth());
			System.out.println(message.toString());
			//jedisStore.hset(jedisStore.getStoreErrorNameAtDepth(currentDepth), state.getKey(), SerializationUtilsExt.serializeToStr(message));
		}
	}
}
