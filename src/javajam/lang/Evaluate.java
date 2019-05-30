package javajam.lang;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import javajam.lang.tokens.NumberToken;
import javajam.lang.tokens.Token;

public class Evaluate {
	/*
	 * Gamma function from https://introcs.cs.princeton.edu/java/91float/Gamma.java.html
	 */
	static double logGamma(double x) {
	      double tmp=(x-0.5)*Math.log(x+4.5)-(x+4.5);
	      double ser=1.0+76.18009173/(x+0)-86.50532033/(x+1)+24.01409822/(x+2)-1.231739516/(x+3)+0.00120858003/(x+4)-0.00000536382/(x+5);
	      return tmp+Math.log(ser*Math.sqrt(2*Math.PI));
	   }
	static double gamma(double x) {
		return Math.exp(logGamma(x));
	}
	
	/*
	 * By Human-v2
	 */
	public static double EvaluateMonomial(List<Token> monomial) {
		double monSum=0;
		int ms=monomial.size();
		boolean mult=true;
		monSum=1;
		if(monomial.size()==0) {
			monSum=0;
		}
		for(int j=0;j<ms;j++) {
			String x=monomial.get(j).getValue();
			if(x.equals("*")) {
				mult=true;
			}else if(x.equals("/")) {
				mult=false;
			}else if(x.equals("^")){
				double val=Double.parseDouble(monomial.get(j-1).getValue());
				double v;
				if(monomial.get(j+1).getValue().equals("-")) {
					v=Math.pow(Double.parseDouble(monomial.get(j-1).getValue()),-1*Double.parseDouble(monomial.get(j+2).getValue()));
					j++;
				}else {
					v=Math.pow(Double.parseDouble(monomial.get(j-1).getValue()),Double.parseDouble(monomial.get(j+1).getValue()));
				}
				if(mult) {
					monSum/=val;
					monSum*=v;
				}else {
					monSum*=val;
					monSum/=v;
				}
				j++;
			}else if(x.equals("sqrt")){
				double v;
				if(monomial.get(j+1).getValue().equals("-")) {
					v=Double.parseDouble(monomial.get(j+2).getValue())*-1;
					j++;
				}else {
					v=Double.parseDouble(monomial.get(j+1).getValue());
				}
				if(mult) {
					monSum*=Math.sqrt(v);
				}else {
					monSum/=Math.sqrt(v);
				}
				j++;
			}else if(x.equals("ln")){
				double v;
				if(monomial.get(j+1).getValue().equals("-")) {
					v=Double.parseDouble(monomial.get(j+2).getValue())*-1;
					j++;
				}else {
					v=Double.parseDouble(monomial.get(j+1).getValue());
				}
				if(mult) {
					monSum*=Math.log(v);
				}else {
					monSum/=Math.log(v);
				}
				j++;
			}else if(x.equals("cbrt")){
				double v;
				if(monomial.get(j+1).getValue().equals("-")) {
					v=Double.parseDouble(monomial.get(j+2).getValue())*-1;
					j++;
				}else {
					v=Double.parseDouble(monomial.get(j+1).getValue());
				}
				if(mult) {
					monSum*=Math.cbrt(v);
				}else {
					monSum/=Math.cbrt(v);
				}
				j++;
			}else if(x.equals("!")){
				double v=gamma(Double.parseDouble(monomial.get(j-1).getValue())+1);
				if(mult) {
					monSum/=Double.parseDouble(monomial.get(j-1).getValue());
					monSum*=v;
				}else {
					monSum*=Double.parseDouble(monomial.get(j-1).getValue());
					monSum/=v;
				}
				j+=2;
			}else {
				double v;
				if(x.equals("-")) {
					v=Double.parseDouble(monomial.get(j+1).getValue())*-1;
					j++;
				}else {
					v=Double.parseDouble(x);
				}
				if(mult) {
					monSum*=v;
				}else {
					monSum/=v;
				}
			}
		}
		return monSum;
	}
	public static double EvaluateExpression(List<Token> expression, int depth) {
		int s=expression.size();
		double sum=0;
		double prefix=1;
		List<Token> monomial=new ArrayList<Token>();
		List<Token> build=new ArrayList<Token>();
		for(int i=0;i<s;i++) {
			Token t=expression.get(i);
			String x="";
			if(i!=0) {
				x=expression.get(i-1).getValue();
			}
			if(t.getValue().equals("+") && t.getDepth()==depth && !x.equals("^") && !x.equals("*") && !x.equals("/") && !x.equals("(")) {
				sum+=EvaluateMonomial(monomial)*prefix;
				monomial=new ArrayList<Token>();
				prefix=1;
			}else if(t.getValue().equals("-") && t.getDepth()==depth && !x.equals("^") && !x.equals("*") && !x.equals("/") && !x.equals("(")) {
				sum+=EvaluateMonomial(monomial)*prefix;
				monomial=new ArrayList<Token>();
				prefix=-1;
			}else if(t.getValue().equals("(") && t.getDepth()==depth) {
				build=new ArrayList<Token>();
				while(!expression.get(i).getValue().equals(")") || expression.get(i).getDepth()!=depth) {
					i++;
					build.add(expression.get(i));
				}
				monomial.add(new NumberToken(EvaluateExpression(build,depth+1),depth));
			}else {
				if(t.getDepth()==depth) {
					monomial.add(t);
				}
			}
			
		}
		sum+=EvaluateMonomial(monomial)*prefix;
		if(expression.size()==0) {
			sum=0;
		}
		return sum;
	}
	public static double EvaluateExpression(List<Token> expression) {
		return EvaluateExpression(expression,0);
	}
	public static double EvaluateExpression(List<Token> expression,HashMap<String,Double> map) {
		for(int i=0;i<expression.size();i++) {
			Token t=expression.get(i);
			if(t.getType()==2) {
				if(map.containsKey(t.getValue())) {
					expression.set(i, new NumberToken(map.get(t.getValue()),t.getDepth()));
				}else {
					System.out.println("Error: No variable in key for "+t.getValue());
					System.exit(0);
				}
			}
		}
		System.out.println("Variables set to "+map.toString());
		return EvaluateExpression(expression,0);
	}
	public static double EvaluateExpression(String s,HashMap<String,Double> map) {
		return EvaluateExpression(Parser.parseExpression(s),map);
	}
	public static double EvaluateExpression(String s) {
		return EvaluateExpression(Parser.parseExpression(s));
	}
}
