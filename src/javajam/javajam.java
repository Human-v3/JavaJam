package javajam;

import java.util.HashMap;

import javajam.lang.Evaluate;
import javajam.lang.Parser;

public class JavaJam {
	public static void main(String[] args) {
		if(args.length > 0) {//Check for expression
			HashMap<String, Double> map = new HashMap<String, Double>();//Variable dictionary
			for(int i = 1; i < args.length; i++) {
				String[] var=args[i].split("=");
				if(var.length < 2) {//Check for variable settings
					System.out.println("Error: argument '" + args[i] + "' is invalid.");
					System.exit(0);
				}else {
					map.put(var[0], Double.parseDouble(var[1]));//Add variable to map
				}
			}
			System.out.println("Solved as " + Evaluate.EvaluateExpression(Parser.parseExpression(args[0]), map));
		}else {
			System.out.println("You must input an expression.");
		}
	}
}
