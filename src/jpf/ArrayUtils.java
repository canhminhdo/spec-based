package jpf;

public class ArrayUtils {
	public static void main(String[] args) {
		// Avoid NullPointerException -> reducing null check
		int [] listeners = new int[0];
		// NullPointerException will occur
		int [] test_listeners = null;
		
		for (int x : listeners) {
			System.out.println(x);
		}
		
//		for (int y : test_listeners) {
//			System.out.println(y);
//		}
		
		System.out.println(" Q.37 Can you store string in array of integers. Try it.");

        String str="I am Akash";
        int arr[]=new int[str.length()];
        char chArr[]=str.toCharArray();
          char  ch;
        for(int i=0;i<str.length();i++)
        {

            arr[i]=chArr[i];
        }
        System.out.println("\nI have stored it in array by using ASCII value");
        for(int i=0;i<arr.length;i++)
        {

            System.out.print(" "+arr[i]);
        }
        System.out.println("\nI have stored it in array by using ASCII value to original content");
        for(int i=0;i<arr.length;i++)
        {
             ch=(char)arr[i];

            System.out.print(" "+ch);
        }
	}
}
