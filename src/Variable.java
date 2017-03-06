import java.util.ArrayList;
import java.util.Collections;


public class Variable {
	char var;
	ArrayList<Integer> values;
	
	public Variable() {
		var = 'A';
		values = new ArrayList<Integer>();
	}
	
	public Variable(String input) {
		var = input.charAt(0);
		values = new ArrayList<Integer>();
		String[] temp = input.substring(3).split(" ");
		for(String s:temp) {
			values.add(Integer.parseInt(s));
		}
	}
}