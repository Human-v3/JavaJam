package javajam.lang.tokens;

public class OperatorToken extends Token{ //For storing Operator strings. Type int is 1.
	private String value;
	public OperatorToken(String val, int dep) { //Constructor
		super(dep);
		value=val;
	}
	public int getType() { //Returns type integer.
		return 1;
	}
	public String getValue() { //Overrides getValue in Token.java
		return value;
	}
}
