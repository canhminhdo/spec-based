package test;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import application.service.TestCaseService;
import jpf.Node;
import jpf.abp.AbpConfiguration;
import junit.framework.TestCase;
import main.abp.Cell;
import main.abp.Channel;
import main.abp.Pair;

public class ConfigurationTest extends TestCase {
	protected Node<AbpConfiguration<String>> node1 = new Node<AbpConfiguration<String>>();
	protected Node<AbpConfiguration<String>> node2 = new Node<AbpConfiguration<String>>();
	
	@Before 
	public void setUp() {
		
		List<application.model.TestCase> testCases = TestCaseService.getAllTestCases();
		System.out.println(testCases);
		
		
		String packets[] = { "0", "1", "2", "3" };
		String receiver1[] = { "0" };
		String receiver2[] = { "0" };
		Channel<Pair<String,Boolean>> ch1 = new Channel<Pair<String,Boolean>>(2);
        Channel<Boolean> ch2 = new Channel<Boolean>(1);
        ch1.put(new Pair<String, Boolean>("0", true));
        ch1.put(new Pair<String, Boolean>("1", false));
        ch2.put(false);
        
		// Node 1
		AbpConfiguration<String> config1 = new AbpConfiguration<String>();
		config1.setIndex(1);
		config1.setFlag1(true);
		config1.setFlag2(true);
		config1.setFinish(new Cell<Boolean>(true));
        config1.setPacketsToBeSent(Arrays.asList(packets));
        config1.setPacketsReceived(Arrays.asList(receiver1));
        config1.setChannel1(ch1);
        config1.setChannel2(ch2);
        node1.setData(config1);
        System.out.println(config1);
        
        // Node 2
		AbpConfiguration<String> config2 = new AbpConfiguration<String>();
		config2.setIndex(1);
		config2.setFlag1(true);
		config2.setFlag2(true);
		config2.setFinish(new Cell<Boolean>(true));
        List<String> list2 = Arrays.asList(packets);
        config2.setPacketsToBeSent(Arrays.asList(packets));
        config2.setPacketsReceived(Arrays.asList(receiver2));
        config2.setChannel1(ch1);
        config2.setChannel2(ch2);
        node2.setData(config2);
        System.out.println(config2);
	}
	
	@Test
	public void testEqual() {
		assertTrue(node1.getData().equals(node2.getData()));
	}
	
}
