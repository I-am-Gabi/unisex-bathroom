package concurrent.unisexbathroom;

import java.util.ArrayList; 
import java.util.List; 
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Bathroom {
	// TODO: define value in config file
	private static final int CAPACITY = 5;
	 
	private Gender sexOcupation;
	private List<Person> bathroom_users;
	
	private Lock block;
	private Condition unavailable;
	private Condition available;
	
	BathroomLine line;
	
	public Bathroom(BathroomLine line) { 
		this.sexOcupation = null;
		this.bathroom_users = new ArrayList<Person>();
		
		this.line = line;
		
		this.block = new ReentrantLock();
		this.unavailable = block.newCondition();
		this.available = block.newCondition();
	}
	
	public void getin(Person p) { 
		block.lock();
		try { 
			// if the bathroom is full or if it's ocupated by oposite gender, people stop to get in 
			while (!this.isAvailable(p.getGender())) {
				System.out.println(p.getGender() + " person " + p.getName() + " tried get in");
				System.out.println("The bathroom is full or unavailable");
				
				unavailable.await();
			} 
			
			if (this.sexOcupation != p.getGender())
				this.sexOcupation = p.getGender();
			bathroom_users.add(p);
			System.out.println(p.getGender() + " person " + p.getName() + " get in");
			
			if (this.isAvailable(p.getGender())) 
				available.signalAll();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			block.unlock();
		}
	}
	
	public synchronized void getout(Person p) { 
		block.lock();
		try { 
			while (this.isAvailable(p.getGender())) {
				System.out.println("The bathroom is available");
				available.wait();
			} 
			 
			bathroom_users.remove(p);
			System.out.println("person " + p.getName() + " get out");  
			
			if (!this.isAvailable(p.getGender())) 
				this.unavailable.signalAll();
			
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			block.unlock();
		}
	} 
	
	public synchronized boolean isAvailable(Gender gender) {
		return (this.sexOcupation == null ||
				this.bathroom_users.isEmpty() ||
				(this.sexOcupation.equals(gender) 
						&& !this.isFull()));
	}
	
	public boolean isFull() {
		return this.bathroom_users.size() == CAPACITY;
	}
}
