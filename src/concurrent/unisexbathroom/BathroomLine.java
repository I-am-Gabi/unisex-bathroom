package concurrent.unisexbathroom;

public class BathroomLine {
	private Person line[];
	
	public BathroomLine (int n) {
		// TODO: dinamico
		this.line = new Person[n];
	}
	
	public void wait(int target) {
		synchronized (line[target]) {
			try {
				line[target].wait();
			} catch (InterruptedException e) { 
				e.printStackTrace();
			}
		}
	}
	
	public void notify(int target) {
		synchronized (line[target]) {
			line[target].notify();
		}
	}
}
