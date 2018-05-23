package concurrent.unisexbathroom;

import java.util.ArrayList; 
import java.util.List; 
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Class to represents the Bathroom. 
 * Constant CAPACITY defines the total capacity of the bathoom.
 * 
 * @author Gabriela Cavalcante and Irene Ginani
 * @version 20/05/2018
 */
public class Bathroom { 
	private static final int CAPACITY = 5;
	 
	private Gender sexOcupation;
	private List<Person> bathroom_users;
	
	private Lock block; 
	private Condition available;
	
	BathroomLine line;
	
	public Bathroom(BathroomLine line) { 
		this.sexOcupation = null;
		this.bathroom_users = new ArrayList<Person>();
		
		this.line = line;
		
		this.block = new ReentrantLock(); 
		this.available = block.newCondition();
	}
	
	public void getin(Person p) { 
		block.lock();
		try { 
			// if the bathroom is full or if it's occupied by the opposite gender, people stop to get in 
			while (this.isUnavailable(p.getGender())) {
				System.out.println(p.getGender() +" "+ p.getName() + " tried to get in");
				System.out.println(" [status] The bathroom is full or unavailable");
				System.out.println(" [person] " + bathroom_users.size());
				available.await();
			} 
			
			if (this.sexOcupation != p.getGender())
				this.sexOcupation = p.getGender();
			
			bathroom_users.add(p);
			System.out.println(p.getGender() + " " + p.getName() + " get in");
			System.out.println(" [person] " + bathroom_users.size()); 
		} catch (InterruptedException e) {
			e.printStackTrace();
		} finally {
			block.unlock();
		}
	}
	
	public void getout(Person p) { 
		block.lock();
		try { 
			bathroom_users.remove(p);
			System.out.println(p.getName() + " get out");  
			
			this.available.signalAll();
		} finally {
			block.unlock();
		}
	} 
	
	public synchronized boolean isUnavailable(Gender gender) {
		return (this.sexOcupation != null || 
				!this.sexOcupation.equals(gender) || 
				this.isFull());
	}
	
	public boolean isFull() {
		return this.bathroom_users.size() == CAPACITY;
	}
}
