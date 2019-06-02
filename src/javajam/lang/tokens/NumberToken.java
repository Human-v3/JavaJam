package javajam.lang.tokens;

public class NumberToken extends Token{ //For storing numerical values, double based. Type int is 3.
	private double value;
	public NumberToken(double val, int dep) {
		super(dep);
		value=val;
	}
	public int getType() { //returns the integer corresponding to its type
		return 3;
	}
	public String getValue() { //To override getValue in Token.java
		return value+"";
	}
}
