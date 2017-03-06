import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Driver {
	
	public void go(ArrayList vars, ArrayList cons, boolean useCEP) {
		
	}
	
	
	/**
     * Main method.
     * @param args the commandline arguments
     */
	public static void main(String[] args) {
		//Check if the number of arguments is valid
		if (args.length != 3) {
            System.err.println("Incorrect number of arguments.");
            System.exit(1);
        }
		
		String varFile = args[0]; //Retrieves the filename of the .var file
		String conFile = args[1]; //Retrieves the filename of the .con file
		
		ArrayList<Variable> vars = new ArrayList<Variable>(); //Stores all of the information in the .var file
		ArrayList<Constraint> cons = new ArrayList<Constraint>(); //Stores all of the information in the .con file
		boolean useCEP; //True if a Consistence-Enforcing Procedure is going to be used
		
		
		try {
			//Get the information from the .var file
            Scanner varin = new Scanner(new File(varFile));
			while(varin.hasNextLine()) {
				vars.add(new Variable(varin.nextLine()));
			}
        }
		catch (FileNotFoundException e) {
            System.err.printf("Could not find file: %s%n", varFile);
            System.exit(1);
        }
		try {
			//Get the information from the .con file
			Scanner conin = new Scanner(new File(conFile));
			while(conin.hasNextLine()) {
				cons.add(new Constraint(conin.nextLine()));
			}
		}
		catch (FileNotFoundException e) {
            System.err.printf("Could not find file: %s%n", conFile);
            System.exit(1);
        }
		
		//Check if the third argument is valid
		if (!args[2].equals("none")&&!args[2].equals("fc")) {
			System.err.printf("Invalid flag: %s%n", args[2]);
			System.exit(1);
		}
		useCEP = args[2].equals("fc");
		
		//Run the main program
		new Driver().go(vars, cons, useCEP);
	}
}