package javajam.lang.tokens;

public class NumberToken extends Token{
	private double value;
	public NumberToken(double val, int dep) {
		super(dep);
		//System.out.println(val);
		value=val;
	}
	public int getType() {
		return 3;
	}
	@Override
	public String getValue() {
		return value+"";
	}
}