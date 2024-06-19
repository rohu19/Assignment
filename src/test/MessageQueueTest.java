package test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

import org.junit.Test;

import main.Consumer;
import main.MessageLogger;
import main.MessageQueue;
import main.Producer;

public class MessageQueueTest {

	@Test
	public void testMessageQueueConcurrency() {
		MessageLogger logger = new MessageLogger();
		MessageQueue queue = new MessageQueue(logger);
		Producer producer = new Producer(queue, logger);
		Consumer consumer = new Consumer(queue, logger);

		Thread[] producerThreads = new Thread[5];
		Thread[] consumerThreads = new Thread[5];

		for (int i = 0; i < 5; i++) {
			producerThreads[i] = new Thread(producer);
			consumerThreads[i] = new Thread(consumer);

			producerThreads[i].start();
			consumerThreads[i].start();
		}

		for (int i = 0; i < 5; i++) {
			try {
				producerThreads[i].join();
				consumerThreads[i].join();
			} catch (InterruptedException e) {
				e.printStackTrace();
				fail("Threads interrupted unexpectedly.");
			}
		}

		assertEquals(500, logger.getSuccessCount());
		assertEquals(0, logger.getErrorCount());
		assertEquals(0, queue.getSize());
	}
}
