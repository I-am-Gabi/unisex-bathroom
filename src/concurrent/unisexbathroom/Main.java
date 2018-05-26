package concurrent.unisexbathroom;
 

/**
 * Main Class.
 * 
 * @author Gabriela Cavalcante and Irene Ginani
 * @version 20/05/2018
 */
public class Main {
	private static final int total = 10;

	public static void main(String[] args) {  
		BathroomLine line = new BathroomLine();
		Bathroom bathroom = new Bathroom(line);
	    Person[] folk = new Person[total];
	    
	    for (int i = 0; i < total; i++) { 
	    		folk[i] = new Person("person " + i, bathroom);   
	    		folk[i].start();
	    } 
	} 
}
