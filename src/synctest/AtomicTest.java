package synctest;

import java.util.concurrent.atomic.AtomicInteger;

public class AtomicTest {
	
    public static void main(String[] args) {
	System.out.println("Current Java version: " + System.getProperty("java.version"));


	AtomicCounter counter = new AtomicCounter();
		
	Worker worker1 = new Worker(counter);
	Worker worker2 = new Worker(counter);
		
	worker1.start();
	worker2.start();
		
	try {
	    worker1.join();
	    worker2.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}
		
	System.out.println("Final value of counter = " + counter.getCount());
    }
	
    static class Worker extends Thread {
		
	private AtomicCounter counter;
		
	public Worker(AtomicCounter counter) {
	    this.counter = counter;
	}
		
	public void run() {
	    counter.increment();
	}
    }

}

class AtomicCounter {
	//	private int value = 0;
	private AtomicInteger value = new AtomicInteger(0);

	public synchronized int getCount() {
		return value.get();
	}

	public void increment() {
		// XXX: Note, load, increment, and store happened atomically!
		// XXX: Delay not possible at all!
		value.incrementAndGet();
	}

}

