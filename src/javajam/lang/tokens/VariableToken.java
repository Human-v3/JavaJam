package javajam.lang.tokens;

public class VariableToken extends Token{ //For storing variable values. Type int is 2.
	private String value;
	public VariableToken(String val, int dep) { //Constructor
		super(dep);
		value=val;
	}
	public int getType() { //Returns integer value for type.
		return 2;
	}
	public String getValue() { //Overrides getValue in Token.java
		return value;
	}
}
