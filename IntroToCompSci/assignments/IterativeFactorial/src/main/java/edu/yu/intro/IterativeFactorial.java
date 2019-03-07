
package	edu.yu.intro;

import java.util.Scanner;

public class IterativeFactorial {
	public static void main (final String [] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Enter ‘n‘ -- I will calculate ‘factorial(n)‘: ");
		long total = 1;
		long FirstNumber = input.nextInt();
		long number = FirstNumber;
		
		if (number < 1) {
			System.out.println("error: integer input must be positive ");
			System.exit(0);
		}
		
		while (number >= 1) {
			total = total * number;
			number = number - 1;
		}	
		
		System.out.printf("factorial(%d) = %d%n ", FirstNumber, total);
	}
}