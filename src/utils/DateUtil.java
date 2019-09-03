package utils;

import java.util.Date;

/**
 * Date Utility
 * 
 * @author OgataLab
 *
 */
public class DateUtil {
	
	/**
	 * Get time from now
	 * 
	 * @return {@link Long}
	 */
	public static long getTime() {
		Date date= new Date();
		return date.getTime();
	}
}
