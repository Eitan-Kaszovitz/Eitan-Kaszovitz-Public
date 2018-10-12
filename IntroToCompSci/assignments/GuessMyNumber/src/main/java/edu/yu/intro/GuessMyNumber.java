
package	edu.yu.intro;

import java.util.Random;
import java.util.Scanner;


public class GuessMyNumber {
	public static void main (final String [] args) {
		Scanner input = new Scanner (System.in);
		final Random random = new Random ();
		System.out.println("I’m thinking of a number between 1 and 100");
		System.out.println("(inclusive). Can you guess what number it is?");
		System.out.print("Please type a number: ");
		int c = input.nextInt();
		System.out.println("You Guessed " + c);
		System.out.print("I was thinking of ");
		final int number = random.nextInt(100) + 1;
		System.out.println(number);
		int g = (number - c);
		System.out.print("You were off by: ");
		System.out.println(Math.abs(g));
	}
}

