package concorrentes.imd.ufrn;

/**
 * 
 * @author Gabriela Cavalcante and Irene Ginani
 * @version 20/05/2018
 */
public class Main {

	public static void main(String[] args) {  
		Bathroom bathroom = new Bathroom();
	    Person[] processos = new Person[10];
	    
	    for (int i = 0; i < 10; i++) { 
	        processos[i] = new Person("person " + i, bathroom); 
	        processos[i].start();
	    }
	    
	    for (int i = 0; i < 10; i++) {   
	        try {
				processos[i].join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	    }
	} 
}
