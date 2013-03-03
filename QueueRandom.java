package main;

import java.util.ArrayDeque;
import java.util.Queue;

public class QueueRandom implements Randomaze{
	Queue<Boolean> queue = new ArrayDeque<Boolean>();

	public QueueRandom(Queue<Boolean> O){
		this.queue = O;
	}
	
	public boolean newLevel() {
		if (queue.isEmpty())
			return false;
		return queue.poll();
	}
}
