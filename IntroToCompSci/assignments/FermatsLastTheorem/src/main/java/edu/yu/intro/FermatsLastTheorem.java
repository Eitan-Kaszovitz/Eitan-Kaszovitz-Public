
package	edu.yu.intro;

import java.util.Scanner;

public class FermatsLastTheorem {
	public static void main (final String [] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Fermat Last Theorem checker: Enter (integers) ’a’, ’b’, ’c’, ’n’: ");
		float a = input.nextFloat();
		float b = input.nextFloat();
		float c = input.nextFloat();
		float n = input.nextFloat();
		boolean x = (a != Math.round(a));
		boolean y = (b != Math.round(b));
		boolean z = (c != Math.round(c));
		boolean p = (n != Math.round(n));
		float a2 = (float) Math.pow(a, n); 
		float b2 = (float) Math.pow(b, n); 
		float c2 = (float) Math.pow(c, n); 
		boolean total = (a2 + b2) == c2;
		boolean NonTotal = (a2 + b2) != c2;
		System.out.println("************************************************** ");
		System.out.println("INPUT ... ");
		System.out.printf("%6s %f%n", "a:", a);
		System.out.printf("%6s %f%n", "b:", b);
		System.out.printf("%6s %f%n", "c:", c); 
		System.out.printf("%s %f%n", "(coeffecient n):", n);
		System.out.println();
		System.out.println();
		
		if (x) {
			System.out.println("error: program requires integer input: you supplied " + a );
			System.exit(0);
		}
		
		if (y) {
			System.out.println("error: program requires integer input: you supplied " + b);
			System.exit(0);
		}
		
		if (z){
			System.out.println("error: program requires integer input: you supplied " + c);
			System.exit(0);
		}
		
		if (p) {
			System.out.println("error: program requires integer input: you supplied " + n);
			System.exit(0);
		}
		
		if ((a < 1) || (b < 1) || (c < 1) || (n < 1)) {
			System.out.println("error: all input parameters must be positive integers");
			System.exit(0);
		}
		
		System.out.println("OUTPUT ... ");
		int ia2 = (int) a2;
		int ib2 = (int) b2; 
		int ic2 = (int) c2;
		System.out.println("Evaluating " + ia2 + " + " + ib2 + " ==? " + ic2 + ": " + total);
		
		if ((n > 2) && (NonTotal)) {
		System.out.println("Hmm ... you haven’t disproved Fermat’s theorem yet.");
		}  
		
		if (((n == 1) || (n == 2)) && (NonTotal)) {
		System.out.println("The sums are not equal but irrelevant to Fermat’s theorem which applies to n > 2");
		}
		
		if (((n == 1) || (n == 2)) && (total)) {
		System.out.println("The sums are equal but irrelevant to Fermat’s theorem which applies only to n > 2");
		}
		
		System.out.println("************************************************** ");
	}
}
