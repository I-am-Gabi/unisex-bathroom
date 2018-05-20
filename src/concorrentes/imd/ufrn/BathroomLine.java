package concorrentes.imd.ufrn;

import java.util.concurrent.Semaphore;

public class BathroomLine {
	private Semaphore semaphore = new Semaphore(1, true);
	
	public void accessBathroom() {
		try {
			semaphore.acquire();
			int duration = (int) (Math.random() * 60) + 1; 
			System.out.print(Thread.currentThread().getName());
			System.out.print(" access bethroom for " + duration + " sec\n");
			Thread.sleep(duration * 100);
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			semaphore.release();
		}
	}
}
