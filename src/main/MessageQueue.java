package main;

import java.util.LinkedList;
import java.util.Queue;

public class MessageQueue {
	private final Queue<Message> queue;
	private final MessageLogger logger;

	public MessageQueue(MessageLogger logger) {
		this.queue = new LinkedList<>();
		this.logger = logger;
	}

	public synchronized void put(Message message) {
		queue.add(message);
		notify(); 
	}

	public synchronized Message take() throws InterruptedException {
		while (queue.isEmpty()) {
			wait(); 
		}
		return queue.poll();
	}

	public synchronized int getSize() {
		return queue.size();
	}
}
