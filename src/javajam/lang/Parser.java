package javajam.lang;

import java.util.ArrayList;
import java.util.List;

import javajam.lang.tokens.NumberToken;
import javajam.lang.tokens.OperatorToken;
import javajam.lang.tokens.Token;
import javajam.lang.tokens.VariableToken;

public class Parser {
	private static double pi=3.141592653589793;
	private static double e=2.718281828459045;
	private static int openPer=0;
	private static int closePer=0;
	public static Token parseToken(String current) {
		boolean isNum=true;
		boolean containsDigit=false;
		//System.out.println(current);
		String[] cur=current.split("");
		Token tok;
		int countDec=0;
		for(String a:cur) {
			if(a.equals("0")) {
				containsDigit=true;
			}else if(a.equals("1")) {
				containsDigit=true;
			}else if(a.equals("2")) {
				containsDigit=true;
			}else if(a.equals("3")) {
				containsDigit=true;
			}else if(a.equals("4")) {
				containsDigit=true;
			}else if(a.equals("5")) {
				containsDigit=true;
			}else if(a.equals("6")) {
				containsDigit=true;
			}else if(a.equals("7")) {
				containsDigit=true;
			}else if(a.equals("8")) {
				containsDigit=true;
			}else if(a.equals("9")) {
				containsDigit=true;
			}else if(a.equals(".")) {
				countDec++;
				containsDigit=true;
			}else {
				isNum=false;
			}
		}
		if(isNum && countDec<2) {
			tok=new NumberToken(Double.parseDouble(current),openPer-closePer);
		}else if(!isNum && !containsDigit) {
			if(current.toLowerCase().equals("pi")){
				tok=new NumberToken(pi,openPer-closePer);
			}else if(current.toLowerCase().equals("e")){
				tok=new NumberToken(e,openPer-closePer);
			}else {
				if(current.equals("sqrt")) {
					tok=new OperatorToken("sqrt",openPer-closePer);
				}else if(current.equals("ln")) {
					tok=new OperatorToken("ln",openPer-closePer);
				}else if(current.equals("cbrt")) {
					tok=new OperatorToken("cbrt",openPer-closePer);
				}else if(current.equals("!")) {
					tok=new OperatorToken("!",openPer-closePer);
				}else if(!current.equals("")) {
					tok=new VariableToken(current,openPer-closePer);
				}else {
					tok=new Token(openPer-closePer);
				}
			}
		}else {
			System.out.println("Failed to parse "+current);
			tok=new Token(openPer-closePer);
			System.exit(0);
		}
		return tok;
	}
	public static List<Token> parseExpression(String expression){
		expression=expression.replaceAll("\\s+","");
		String[] exp=expression.split("");
		System.out.println("Input set to '"+expression+"'");
		List<Token> tokens=new ArrayList<Token>();
		int openBrack=0;
		int closeBrack=0;
		String current="";
		for(String x:exp) {
			boolean state=false;
			boolean pup=false;
			boolean pdown=false;
			if(x.equals(" ")) {
				
			}else {
				if(x.equals("*")) {
					state=true;
					tokens.add(new OperatorToken("*",openPer-closePer));
				}else if(x.equals("+")) {
					state=true;
					tokens.add(new OperatorToken("+",openPer-closePer));
				}else if(x.equals("-")) {
					state=true;
					tokens.add(new OperatorToken("-",openPer-closePer));
				}else if(x.equals("/")) {
					state=true;
					tokens.add(new OperatorToken("/",openPer-closePer));
				}else if(x.equals("^")) {
					state=true;
					tokens.add(new OperatorToken("^",openPer-closePer));
				}else if(x.equals("=")) {
					state=true;
					tokens.add(new OperatorToken("=",openPer-closePer));
				}else if(x.equals("(")) {
					state=true;
					tokens.add(new OperatorToken("(",openPer-closePer));
					pup=true;
				}else if(x.equals(")")) {
					state=true;
					tokens.add(new OperatorToken(")",openPer-closePer-1));
					pdown=true;
				}else if(x.equals("[")) {
					state=true;
					tokens.add(new OperatorToken("(",openPer-closePer));
					pup=true;
				}else if(x.equals("]")) {
					state=true;
					tokens.add(new OperatorToken(")",openPer-closePer-1));
					pdown=true;
				}else if(x.equals("!")) {
					state=true;
					tokens.add(new OperatorToken("!",openPer-closePer));
				}else {
					state=false;
				}
				if(state) {
					Token t=parseToken(current);
					current="";
					if(t.getType()!=0) {
						if(tokens.size()>1) {
							tokens.add(tokens.size()-1,t);
						}else {
							tokens.add(0,t);
						}
					}
				}else {
					current+=x;
				}
				if(pup) {
					openPer++;
				}else if(pdown) {
					closePer++;
				}
			}
		}
		Token t=parseToken(current);
		if(t.getType()!=0) {
			tokens.add(tokens.size(),t);
		}
		//System.out.println(tokens.toString());
		if(openBrack==closeBrack && openPer==closePer) {
			String sum="";
			for(int i=0;i<tokens.size();i++) {
				sum+=tokens.get(i).getValue();
			}
			System.out.println("Parsed as "+sum);
		}else {
			System.out.println("Failed to parse '"+expression+"'. Perentheses or brackets have unequal openings and closings.");
		}
		openPer=0;
		closePer=0;
		return tokens;
	}
}
