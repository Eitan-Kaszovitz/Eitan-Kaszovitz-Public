
package	edu.yu.intro;

import java.util.Scanner;
import java.io.*;

public class LoopyEmployeePay {	
	public static void main (final String [] args) throws FileNotFoundException {
		if ( args.length != 1) {
			final String msg = " Usage : LoopyEmployeePayname_of_input file ";
			System.err.println(msg);
			throw new IllegalArgumentException(msg) ;
		}
		final String inputFileName = args [0];
		final File input = new File (inputFileName) ;
		Scanner sc = new Scanner (input);
		
		System.out.println("************************************************** ");
		System.out.printf("%21s ", "Id ");
		System.out.printf("%-9s", "Gross Pay");
		System.out.printf("%12s", "Taxes");
		System.out.printf("%12s", "Net Pay");
		System.out.printf("%16s%n", "Average Pay");
		
		int line = 0;
		double totalGross = 0;
		double totalTaxes = 0;
		double largestGross = 0;
		String largestGrossId = "none";
		
		while (sc.hasNext()) {
			line++;
			String n = sc.next();
			float h = sc.nextFloat();
			float r = sc.nextFloat();
			int c = sc.nextInt();
			float g = h * r;
			float t = (.15f * g);
			float net = g - t - c - (.05f * g);
			float a = net/h;
			
			if (g > largestGross) {
			largestGrossId = n;
			largestGross = g;
			}
     		
			if (h < 1.0f) {
			System.out.println("Problem at line # " + line + ": ’hrs worked’ must be at least 1 -- but found " + h);
			}
		
			if (r < 15.0f) {
			System.out.println("Problem at line # " + line + ": 'wage rate' must be at least 15 -- but found " + r);
			}
		
			if ((c <= 0) || (c >= 35)) {
			System.out.println("Problem at line # " + line + ": deductions must be between 0 and 35 -- but found " + c);
			}
		
			if (net < 0) {
			System.out.println("Problem at line # " + line + ": Net pay must be positive -- but found " + net);
			}
		
			if ((h >= 1.0f) && (r >= 15.0f) && (c > 0) && (c < 35) && (net >= 0)) {	
			System.out.printf("%21s ", n);
			System.out.printf("%-9.2f ", g);
			System.out.printf("%11.2f ", t);
			System.out.printf("%11.2f ", net);
			System.out.printf("%13.2f%n", a);
			totalGross += g;
			totalTaxes += t;
			}
			
			
		}	
		System.out.println();
		System.out.printf("%37s %.2f%n", "Total Gross Pay: ", totalGross);
		System.out.printf("%37s %.2f%n", "Total Taxes: ", totalTaxes);
		System.out.printf("%37s %s%n", "Employee with Largest Gross Pay: ", largestGrossId);
		System.out.println("************************************************** ");
	}
}
