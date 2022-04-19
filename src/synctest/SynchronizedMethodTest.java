package synctest;

public class SynchronizedMethodTest {
	
    public static void main(String[] args) {
	System.out.println("Current Java version: " + System.getProperty("java.version"));

	/*
	Creates a new counter object which contains:
	getCount()
	increment()
	delay()
	 */
	Counter counter = new Counter(); 

	/*
	Creates two workers and start them.
	Workers perform work on one or more background threads.
	 */
	Worker worker1 = new Worker(counter,1);
	Worker worker2 = new Worker(counter,2);
		
	worker1.start();
	worker2.start();
		
	try {
		// End the workers after they finish.
	    worker1.join();
	    worker2.join();
	} catch (InterruptedException e) {
	    e.printStackTrace();
	}

	// Print out the final value of the counter.
	System.out.println("Final value of counter = " + counter.getCount());
    }

    /*
    The worker class. Needs to be defined to create a worker.
     */
    static class Worker extends Thread {
		private Counter counter;
		private int id;

		/*
		The worker stores the counter and the id of the counter.
		 */
		public Worker(Counter counter, int id) {
			this.counter = counter;
			this.id = id;
		}

		/*
		The worker will increment the counter.
		Note that the increment method is synchronized.
		 */
		public void run() {
			for (int i=0; i<10; i++) {
				counter.increment();
			}
			// if (id == 1) {
			// 	counter.increment();
			// } else {
			// 	counter.somethingElse();
			// }
		}
    }

}

class Counter {
	private int value = 0;

	public int getCount() {
		return value;
	}

	/*
    Synchronized methods
    If a method is synchronized, then only a single thread will be able to run whatever
    is in the method at one time.
    Synchronized methods are utilized to prevent race conditions
    (race condition is when the variable is replaced by an incorrect value as a result of reading outdated values).
     */
	public synchronized void increment() {
		// critical section (start)

		System.out.println("Thread " + Thread.currentThread().getId() + " in increment (start)...");
		int register = value;
		delay();        // Might represent some I/O
		register = register + 1;
		delay();        // might represent some I/O
		value = register;

		System.out.println("Thread " + Thread.currentThread().getId() + " in increment (end)...");

		// critical section (end)
	}

	private void delay() {
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}

}

