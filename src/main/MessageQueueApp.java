package main;

public class MessageQueueApp {
	public static void main(String[] args) {
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
			Thread.currentThread().interrupt();
		}

		System.out.println("Total messages processed successfully: " + logger.getSuccessCount());
		System.out.println("Total errors encountered: " + logger.getErrorCount());
	}
}
