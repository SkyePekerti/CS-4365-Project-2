import java.util.ArrayList;

public class Variable implements Comparable<Variable> {

	char var;
	ArrayList<Integer> values;
    int numConstraints;
	
	public Variable() {
		var = 'A';
		values = new ArrayList<>();
        numConstraints = 0;
	}
	
	public Variable(String input) {
		var = input.charAt(0);
		values = new ArrayList<>();
		String[] temp = input.substring(3).split(" ");
		for(String s:temp) {
			values.add(Integer.parseInt(s));
		}
	}

	public int compareTo(Variable other) {
        if (values.size() == other.values.size()) {
            if (numConstraints == other.numConstraints) {
                return var - other.var;
            }
            return other.numConstraints - numConstraints;
        }
        return values.size() - other.values.size();
	}

	public Variable copyOf() {
        Variable copy = new Variable();
        copy.var = var;
        copy.values.addAll(values);
        copy.numConstraints = numConstraints;
        return copy;
    }
}