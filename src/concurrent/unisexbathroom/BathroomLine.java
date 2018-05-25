package concurrent.unisexbathroom;

import java.util.LinkedList;
import java.util.Queue;

/**
 * [WIP] Bathroom line 
 * 
 * @author Gabriela Cavalcante and Irene Ginani
 * @version 22/05/2018
 */
public class BathroomLine {
	private Queue<Person> line;
	
	public BathroomLine (int n) { 
		this.line = new LinkedList<>();
	} 
	
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
	
	public void notifyThread() { 
		Person p = line.poll();
		if (p != null) {
			synchronized (p) {
				p.notify();
			}
		}
	}
	
	public boolean contains(Person p) {
		return line.contains(p);
	}

	public boolean hasPerson() {
		return !line.isEmpty();
	}
}
