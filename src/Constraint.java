


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
}