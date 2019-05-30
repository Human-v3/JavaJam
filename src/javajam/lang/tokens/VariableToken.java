package javajam.lang.tokens;

public class VariableToken extends Token{
	private String value;
	public VariableToken(String val, int dep) {
		super(dep);
		//System.out.println(val);
		value=val;
	}
	public int getType() {
		return 2;
	}
	public String getValue() {
		return value;
	}
}