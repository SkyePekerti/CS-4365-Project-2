import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
import java.util.Map;
import java.util.HashMap;

public class Driver {

	public void go(ArrayList<Variable> vars, ArrayList<Constraint> cons, boolean useCEP) {
        for (Constraint c : cons) {
            for (Variable v : vars) {
                if (c.var1 == v.var || c.var2 == v.var) {
                    v.numConstraints++;
                }
            }
        }
        solve(new State(vars, cons, useCEP));
	}
	
	/**
     * Follows the Backtracking Search Algorithm to solve input.
     * @param args the commandline arguments
	 * @return true if the state is valid
     */
	public boolean solve(State currState) {
        if (currState.isSolved()) {
            System.out.println(currState);
            return true;
        }
        currState.selectNextVar();
        for (int val : currState.getOrderedVals()) {
            State nextState = currState.copyOf();
            nextState.setVar(val);
            if (nextState.consistent()) {
                if (solve(nextState)) {
                    return true;
                }
            } else {
                System.out.println(nextState);
            }
        }
        return false;
    }

	/**
     * Parses the arguments from the command line.
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
		
		ArrayList<Variable> vars = new ArrayList<>(); //Stores all of the information in the .var file
		ArrayList<Constraint> cons = new ArrayList<>(); //Stores all of the information in the .con file
		boolean useCEP; //True if a Consistence-Enforcing Procedure is going to be used
		
		
		try {
			//Get the information from the .var file
            Scanner varin = new Scanner(new File(varFile));
			while(varin.hasNextLine()) {
				vars.add(new Variable(varin.nextLine().trim()));
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
				cons.add(new Constraint(conin.nextLine().trim()));
			}
		}
		catch (FileNotFoundException e) {
            System.err.printf("Could not find file: %s%n", conFile);
            System.exit(1);
        }
		
		//Check if the third argument is valid
		if (!args[2].equals("none") && !args[2].equals("fc")) {
			System.err.printf("Invalid flag: %s%n", args[2]);
			System.exit(1);
		}
		useCEP = args[2].equals("fc");
		
		//Run the main program
		new Driver().go(vars, cons, useCEP);
	}
}