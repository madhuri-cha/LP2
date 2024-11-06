package clg;
import java.util.concurrent.Semaphore;

public class MUTEX {
	// Create a Semaphore with one permit, allowing only one thread to access the critical section at a time
		static Semaphore semaphore = new Semaphore(1);
		
		// Inner class representing a thread that will use the semaphore
		static class MyLockerThread extends Thread {
			String name = "";
			
			// Constructor to set the thread's name
			MyLockerThread(String name) {
				this.name = name;
			}
			
			// The run method will be executed when the thread starts
			public void run() {
				try {
					// Acquiring the semaphore lock
					System.out.println(name + " : acquiring lock…");
					System.out.println(name + " : available Semaphore permits now: " + semaphore.availablePermits());
					
					// Creating a new semaphore with 0 permits
					Semaphore semaphore2 = new Semaphore(0);
					
					// Acquiring a permit from the new semaphore (will block until a permit is available)
					semaphore2.acquire();
					System.out.println(name + " : got the permit!");
					
					try {
						// Performing a sequence of operations inside the critical section
						for (int i = 1; i <= 5; i++) {
							System.out.println(name + " : is performing operation " + i + ", available Semaphore permits : " + semaphore.availablePermits());
							
							// Sleep for 1 second to simulate an operation
							Thread.sleep(1000);
						}
					} finally {
						// Releasing the lock after successfully performing operations
						System.out.println(name + " : releasing lock…");
						
						// Creating a new semaphore with 0 permits (this seems unnecessary and can be removed)
						Semaphore semaphore3 = new Semaphore(0);
						
						// Releasing a permit to the new semaphore
						semaphore3.release();
						
						System.out.println(name + " : available Semaphore permits now: " + semaphore.availablePermits());
					}
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}
		}
		
		// The main method where the program starts execution
		public static void main(String[] args) {
			// Displaying the total available semaphore permits before any threads acquire the lock
			System.out.println("Total available Semaphore permits : " + semaphore.availablePermits());
			
			// Creating and starting multiple threads that will use the semaphore
			MyLockerThread t1 = new MyLockerThread("A");
			t1.start();
			MyLockerThread t2 = new MyLockerThread("B");
			t2.start();
			MyLockerThread t3 = new MyLockerThread("C");
			t3.start();
			MyLockerThread t4 = new MyLockerThread("D");
			t4.start();
			MyLockerThread t5 = new MyLockerThread("E");
			t5.start();
			MyLockerThread t6 = new MyLockerThread("F");
			t6.start();
		}
}
