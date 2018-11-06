
package	edu.yu.intro;

import java.util.Scanner;
import java.lang.IllegalArgumentException;

public class RefactoredFactorial {
	public static void main (final String [] args) {
		System.out.print("Enter ‘n‘ -- I will calculate ‘factorial(n)‘: ");
		Scanner input = new Scanner (System.in);
		int originalNumber = input.nextInt();
		int number = originalNumber;
		if (number < 0){
			throw new IllegalArgumentException();
		}
		long factorialValue = factorial(number);
		System.out.println("****************************************************");
		System.out.printf("factorial(%d) = %d%n", originalNumber, factorialValue);
		System.out.println("****************************************************");
		
	}
		
	public static long factorial (int n) {
		long number = n;
		if (n < 0){
			throw new IllegalArgumentException();
		}
		long total = 1;
		
		while (number >= 1) {
			total = total * number;
			number--;
		}	
		return total;
	}
}		


/*System.out.printf("factorial(%d) = %d%n ", FirstNumber, total);*/
