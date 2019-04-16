package service;

import java.util.ArrayList;

public class AdapterData {
	public static String stringfyList(ArrayList<String> list) {
		if (list.size() == 0) {
			return null;
		}
		StringBuffer sb = new StringBuffer();
		sb.append(list.get(0));
		for (int i = 1; i < list.size(); i ++) {
			sb.append(",");
			sb.append(list.get(i));
		}
		return sb.toString();
	}
}
