package main;

import java.util.Random;

public class Producer implements Runnable {
	private final MessageQueue queue;
	private final MessageLogger logger;
	private final Random random = new Random();

	public Producer(MessageQueue queue, MessageLogger logger) {
		this.queue = queue;
		this.logger = logger;
	}

	@Override
	public void run() {
		for (int i = 1; i <= 100; i++) {
			try {
				Message message = new Message(i, "Message " + i);
				queue.put(message);
				logger.logSuccess();
				Thread.sleep(random.nextInt(100)); // Simulate some processing time
			} catch (InterruptedException e) {
				logger.logError();
				Thread.currentThread().interrupt();
			}
		}
	}
}
