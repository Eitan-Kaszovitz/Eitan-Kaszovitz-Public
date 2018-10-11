
package	edu.yu.intro;

import java.util.Scanner;

public class OneEmployeePay {
	public static void main (final String [] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Enter employeeId, hrsWorked, wageRate, deductions: ");
		String n = input.next();
		float h = input.nextFloat();
		float r = input.nextFloat();
		int c = input.nextInt();
		float g = h * r;
		float t = (.15f * g);
		float net = g - t - c - (.05f * g);
		float a = net/h;
		System.out.println("************************************************** ");
		System.out.println("INPUT ... ");
		System.out.printf("%-20s %s%n", "Employee ID:", n);
		System.out.printf("%-20s %.2f%n", "Hours Worked:", h);
		System.out.printf("%-20s %.2f%n", "Wage Rate:", r); 
		System.out.printf("%-20s %d%n", "Deductions:", c);
		System.out.println();
		System.out.println();
		System.out.println("OUTPUT ... ");
		System.out.printf("%-20s %.2f%n", "Gross Pay:", g);
		System.out.printf("%-20s %.2f%n", "Taxes:", t);
		System.out.printf("%-20s %.2f%n", "Net pay:", net);
		System.out.printf("%-20s %.2f%n", "Average pay:", a);
		System.out.println("************************************************** ");
	}
}




