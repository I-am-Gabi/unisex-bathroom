package concorrentes.imd.ufrn;

public class Main {

	public static void main(String[] args) {  
		BathroomLine line = new BathroomLine();
	    Person[] processos = new Person[10];
	    for (int i = 0; i < 10; i++) {
	        processos[i] = new Person("person " + i, line);
	        processos[i].start();
	    }
	} 
}
