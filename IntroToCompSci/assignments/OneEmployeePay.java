
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
		System.out.printf("%-20s ", "Employee ID:");
		System.out.printf("%s%n", n);
		System.out.printf("%-20s ", "Hours Worked:");
		System.out.printf("%.2f%n", h);
		System.out.printf("%-20s ", "Wage Rate:"); 
		System.out.printf("%.2f%n", r);
		System.out.printf("%-20s ", "Deductions:");
		System.out.println(c);
		System.out.println();
		System.out.println();
		System.out.println("OUTPUT ... ");
		System.out.printf("%-20s ", "Gross Pay:");
		System.out.printf("%.2f%n", g);
		System.out.printf("%-20s ", "Taxes:");
		System.out.printf("%.2f%n", t);
		System.out.printf("%-20s ", "Net pay:");
		System.out.printf("%.2f%n", net);
		System.out.printf("%-20s ", "Average pay:");
		System.out.printf("%.2f%n", a);
		System.out.println("************************************************** ");
	}
}




