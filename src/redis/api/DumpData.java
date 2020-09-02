package redis.api;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Map;

import jpf.common.OC;
import utils.SerializationUtilsExt;

public class DumpData {
	
	public static void main(String[] argv) {
		String fileName = "analyze/state_sequences.txt";
		int currentDepth = 100;
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
		
	}
}
