package mq;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class ConsumerTest {
	Consumer consumer;

	@Before
	public void setUp() throws Exception {
		consumer = new Consumer();
	}

	@After
	public void tearDown() throws Exception {
	}

	@Test
	public void matchedQueueNameTest() {
		assertTrue(consumer.matchedQueueName("NSPK0"));
		assertFalse(consumer.matchedQueueName("NSPK_DEPTH"));
	}

}
