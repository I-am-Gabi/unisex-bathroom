package concorrentes.imd.ufrn;

public class Person extends Thread {
	private BathroomLine line;
	
	public Person(String name, BathroomLine line) {
		super(name);
		this.line = line;
	}
	
	@Override
	public void run() {
		System.out.println("Get in the bathroom... " + Thread.currentThread().getName());
		line.accessBathroom();
	}
}
