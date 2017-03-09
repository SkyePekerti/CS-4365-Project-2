public class Constraint {
	char var1;
	char var2;
	char operator;
	
	public Constraint() {
		var1 = 'A';
		var2 = 'A';
		operator = '=';
	}
	
	public Constraint(String input) {
		var1 = input.charAt(0);
		var2 = input.charAt(4);
		operator = input.charAt(2);
	}

	public boolean valid(int val1, int val2) {
        switch (operator) {
        case '<':
            return val1 < val2;
        case '>':
            return val1 > val2;
        case '!':
            return val1 != val2;
        case '=':
            return val1 == val2;
        }
        return false;
    }
}