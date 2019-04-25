package main;
import java.util.*;

import gov.nasa.jpf.vm.Verify;

public class TestABP {
    public static void main(String[] args)
        throws InterruptedException
    {
    	Verify.beginAtomic();
    	String packets[] = { "0", "1", "2" };
        List<String> list1 = Arrays.asList(packets);
        List<String> list2 = new ArrayList<String>();
        ABP<String> abp = new ABP<String>();
        Verify.endAtomic();
        abp.begin(list1,list2,3);
//        System.out.println("Packets sent: " + list1);
//        System.out.println("Packets rec: " + list2);
//        assert list1.equals(list2);
    }
}