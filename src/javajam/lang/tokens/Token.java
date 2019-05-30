package javajam.lang.tokens;

public class Token {
	private int depth=0;
	public Token(int dep) {
		depth=dep;
	}
	public int getType() {
		return 0;
	}
	public String getValue() {
		return "1.0";
	}
	public int getDepth() {
		return depth;
	}
}
