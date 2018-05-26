package concurrent.unisexbathroom;

import java.util.ArrayList; 
import java.util.List;  
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
	private BathroomLine line;
	
	private Lock blockout;
	private Lock blockin;
	
	public Bathroom(BathroomLine line) { 
		this.sexOcupation = null;
		this.bathroom_users = new ArrayList<Person>(); 
		this.line = line;
		
		this.blockout = new ReentrantLock(); 
		this.blockin = new ReentrantLock(); 
	}
	
	/**
	 * Put a person inside the bethroom case it's available
	 * @param person that will get in
	 */
	public void getin(Person p) throws InterruptedException { 
		// if the bathroom is full or if it's occupied by the opposite gender, people stop to get in 
		while (this.isUnavailable(p.getGender())) {
			System.out.println(p.getGender() + " " + p.getName() + " tried to get in | [people in the bathroom] " + bathroom_users.size()); 
			blockin.lock();
			line.await(p); 
			blockin.unlock();
		}   
 
		if (this.sexOcupation != p.getGender())
			this.sexOcupation = p.getGender();
		
		bathroom_users.add(p);
		System.out.println(p.getGender() + " " + p.getName() + " get in | [people in the bathroom] " + bathroom_users.size()); 
		line.notifyThread(); 
	}
	
	/**
	 * Method to put the person out.
	 * @param person that will get out
	 */
	public void getout(Person p) {  
		blockout.lock();
		try { 
			bathroom_users.remove(p);
			System.out.println(" >> " + p.getName() + " get out | [people in the bathroom] " + bathroom_users.size());  
			 
			// notify the next person that the someone get out 
			if (this.bathroom_users.isEmpty())
				line.notifyThread(); 
		} finally {
			blockout.unlock();
		}
	} 
	
	/**
	 * Method to check if the bathroom is unabailable to a specifical gender.
	 * Return true if the bathroom is full or if it's occupated by the opposite gender.
	 * @param gender
	 * @return bathroom status
	 */
	public boolean isUnavailable(Gender gender) {
		return (this.sexOcupation != null && this.isFull() ||
				(this.line.hasPerson() && this.bathroom_users.size() != 0) ||
				(!gender.equals(this.sexOcupation) && this.bathroom_users.size() != 0));
	}
	
	/**
	 * Check is the bathroom is full.
	 * @return true if the bathroom is full.
	 */
	public boolean isFull() {
		return this.bathroom_users.size() == CAPACITY;
	}
}
