
package	edu.yu.intro;

import java.util.Scanner;
import java.io.*; 
import java.util.*;
import java.lang.NegativeArraySizeException;
import java.util.InputMismatchException;

public class RefactoredSqrtApproximation {
	public static void main (final String [] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Enter ‘n‘ -- I will calculate all square roots from 0 to n (inclusive) using Babylonian approximation algorithm: ");
		try {
			int number = input.nextInt();
			double [] bigArray = calculateSquareRoots(number + 1);
			double c = 0;
			System.out.println("***********************************************");
			for (int x = 0; x < (bigArray.length); x++) {
				c = bigArray[x];
				System.out.printf("sqrt(%d) = %.3f%n", x, c);
			}
		System.out.println("***********************************************");
		}
		catch (NegativeArraySizeException e){
			System.err.println("error: input can't be negative");
			System.exit(0);
		}
		catch (InputMismatchException e){
			System.err.println("error: input must be positive integer");
			System.exit(0);
		}
	}
	
	public static double [] calculateSquareRoots (int n) {
		int number = n;
		double[] sqrtArray = new double [number];
		for (int i = 0; i < number; i++){
			sqrtArray[i] = sqrt(i);
		}
		return sqrtArray;
	}
	
	
	public static double sqrt (double a) {
		double number = a;
		
		double FirstGuess = number / 2;
		double NextGuess = number / 2.1;
		
		/*if (a <= .001) {
			System.out.println("error: input must be positive and above relative zero");
			System.exit(0);
		}*/
		
		while (Math.abs(FirstGuess - NextGuess) >= 0.0001) {
			FirstGuess = NextGuess;
			double b = (number / FirstGuess);
			NextGuess = (b + FirstGuess) / 2;	
		}
		return NextGuess;
	}


	
}