package concurrent.unisexbathroom;

import java.util.LinkedList;
import java.util.Queue;

/**
 * Class to represents the bathroom line.
 * 
 * @author Gabriela Cavalcante and Irene Ginani
 * @version 22/05/2018
 */
public class BathroomLine {
	private Queue<Person> line; 
	
	public BathroomLine () { 
		this.line = new LinkedList<>();
	} 
	
	/**
	 * Method to put a person in the bathroom line.
	 * @param p person
	 */
	public void await(Person p) {
		line.add(p); 
		synchronized (p) { 
			try {
				p.wait();
			} catch (InterruptedException e) { 
				e.printStackTrace();
			} 
		}
	}
	
	/**
	 * Method to wakeup a person, and put her in the bathroom.
	 */
	public void notifyThread() { 
		Person p = line.poll();
		if (p != null) {
			synchronized (p) {
				p.notify();
			}
		}
	} 

	/**
	 * Check if the bathroom line has someone
	 * @return true if the line isn't empty
	 */
	public boolean hasPerson() {
		return !line.isEmpty();
	}
}
