package main;

import java.util.Random;

public class Consumer implements Runnable {
	private final MessageQueue queue;
	private final MessageLogger logger;
	private final Random random = new Random();

	public Consumer(MessageQueue queue, MessageLogger logger) {
		this.queue = queue;
		this.logger = logger;
	}

	@Override
	public void run() {
		while (true) {
			try {
				Message message = queue.take();
				processMessage(message);
				logger.logSuccess();
				Thread.sleep(random.nextInt(100)); // Simulate some processing time
			} catch (InterruptedException e) {
				logger.logError();
				Thread.currentThread().interrupt();
			}
		}
	}

	private void processMessage(Message message) {
		System.out.println("Processed message with id " + message.getId() + " and content: " + message.getContent());
	}
}
