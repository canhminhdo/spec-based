package utils;

import java.util.ArrayList;

/**
 * This file is a utility for debug printing
 * 
 * @author OgataLab
 *
 */
public class PrettyPrinter {
	
	public static void printHeader() {
		System.out.println("<==============PrettyPinter================");
	}
	
	public static void printList(ArrayList<String> list) {
		PrettyPrinter.printHeader();
		System.out.println("<+> Number of element: " + list.size());
		for (int i = 0; i < list.size(); i ++) {
			System.out.println("[" + (i + 1) + "]" + list.get(i));
		}
		PrettyPrinter.printFooter();
	}
	
	public static void printFooter() {
		System.out.println("==============================>");
	}

}
