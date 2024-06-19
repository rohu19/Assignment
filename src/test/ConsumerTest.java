package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

import main.Consumer;
import main.MessageLogger;
import main.MessageQueue;
import main.Producer;

public class ConsumerTest {

	@Test
	public void testConsumerSuccess() {
		MessageLogger logger = new MessageLogger();
		MessageQueue queue = new MessageQueue(logger);
		Producer producer = new Producer(queue, logger);
		Consumer consumer = new Consumer(queue, logger);

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();
		consumerThread.start();

		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Threads interrupted unexpectedly.");
		}

		assertEquals(100, logger.getSuccessCount());
		assertEquals(0, logger.getErrorCount());
		assertEquals(0, queue.getSize());
	}

	@Test
	public void testConsumerInterrupted() {
		MessageLogger logger = new MessageLogger();
		MessageQueue queue = new MessageQueue(logger);
		Producer producer = new Producer(queue, logger);
		Consumer consumer = new Consumer(queue, logger);

		Thread producerThread = new Thread(producer);
		Thread consumerThread = new Thread(consumer);

		producerThread.start();
		consumerThread.start();

		consumerThread.interrupt();

		try {
			producerThread.join();
			consumerThread.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
			fail("Threads interrupted unexpectedly.");
		}

		assertEquals(100, logger.getSuccessCount());
		assertTrue(logger.getErrorCount() > 0);
		assertTrue(queue.getSize() <= 100);
	}
}
