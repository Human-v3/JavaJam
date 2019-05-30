package javajam.lang.tokens;

public class OperatorToken extends Token{
	private String value;
	public OperatorToken(String val, int dep) {
		super(dep);
		//System.out.println(val);
		value=val;
	}
	public int getType() {
		return 1;
	}
	public String getValue() {
		return value;
	}
}
