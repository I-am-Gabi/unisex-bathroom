package concorrentes.imd.ufrn;

public class Person extends Thread {
	private Gender gender;
	private String name;
	private Bathroom bathroom;
	
	public Person(String name, Bathroom bathroom) {
		super(name); 
		this.bathroom = bathroom;
		this.gender = Gender.getGender();
	}
	
	@Override
	public void run() { 
		while(true) {  
			try {
				Thread.sleep ((long) (500));
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}

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
			
			bathroom.getout(this);
		}
	}

	public Gender getGender() {
		return gender;
	} 
}
