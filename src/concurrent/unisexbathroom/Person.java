package concurrent.unisexbathroom;

import java.util.Random;

public class Person extends Thread {
	private Gender gender; 
	private Bathroom bathroom;
	private int time;
	
	public Person(String name, Bathroom bathroom) {
		super(name); 
		this.bathroom = bathroom;
		this.gender = Gender.getGender();
		
		Random random = new Random();
		this.time =  1; //random.nextInt(1);
	}
	
	@Override
	public void run() {   

		bathroom.getin(this);
		
		synchronized(bathroom){
			try {
				// Wait until can get in 
				bathroom.wait();	
				System.out.println("Get in the bathroom... " + Thread.currentThread().getName());
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		try {
			Thread.sleep ((long) (this.time) * 1000);
		} catch (InterruptedException e) { 
			e.printStackTrace();
		}
		
		bathroom.getout(this); 
	}

	public Gender getGender() {
		return gender;
	} 
}
