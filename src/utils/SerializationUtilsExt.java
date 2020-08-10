package utils;

import java.util.Base64;
import org.apache.commons.lang3.SerializationUtils;
import jpf.common.OC;

public class SerializationUtilsExt {
	
	public static String serializeToStr(OC message) {
		String redisString = new String(Base64.getEncoder().encode(SerializationUtils.serialize(message)));
		return redisString;
	}
	
	public static OC deserialize(String str) {
		byte data[] = Base64.getDecoder().decode(str.getBytes());
		OC message = SerializationUtils.deserialize(data);
		return message;
	}
}
