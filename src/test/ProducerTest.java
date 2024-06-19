package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import main.MessageLogger;
import main.MessageQueue;
import main.Producer;

public class ProducerTest {

	@Test
	public void testProducerSuccess() {
		MessageLogger logger = new MessageLogger();
		MessageQueue queue = new MessageQueue(logger);
		Producer producer = new Producer(queue, logger);

		Thread producerThread = new Thread(producer);
		producerThread.start();

		try {
			producerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Producer thread interrupted unexpectedly.");
		}

		assertEquals(100, logger.getSuccessCount());
		assertEquals(0, logger.getErrorCount());
		assertEquals(100, queue.getSize());
	}
}