
package	edu.yu.intro;

import java.util.Scanner;

public class CelsiusToFahrenheit {
	public static void main (final String [] args) {
		Scanner input = new Scanner (System.in);
		System.out.println("Enter temperature in Celsius: ");
		float c = input.nextFloat();
		float f = 32 + (c * 9/5.0f); 
		System.out.printf("%.1f C = %.1f F ", c, f);
    }   
}


