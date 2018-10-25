
package	edu.yu.intro;

import java.util.Scanner;

public class SqrtApproximation {
	public static void main (final String [] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Enter ‘a‘ -- I will approximate ‘sqrt(a)‘: ");
		double a = input.nextDouble();
		double FirstGuess = a / 2;
		double NextGuess = a / 2.1;
		int iterations = 0;
		
		if (a <= .001) {
			System.out.println("error: input must be positive and above relative zero");
			System.exit(0);
		}
		
		while (Math.abs(FirstGuess - NextGuess) >= 0.0001) {
			FirstGuess = NextGuess;
			double b = (a / FirstGuess);
			NextGuess = (b + FirstGuess) / 2;	
			iterations++;
		}
			double real = Math.sqrt(a);
			double diff = Math.abs(NextGuess - real);
			
			System.out.println();
			System.out.println("*************************************************");
			System.out.printf("sqrt(%f) = %.3f after %d iteration(s): [difference from Math.sqrt= %g]%n", a, NextGuess, iterations, diff);
			System.out.println("*************************************************");



	}
}