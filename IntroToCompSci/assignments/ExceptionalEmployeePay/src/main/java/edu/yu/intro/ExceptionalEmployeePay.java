
package	edu.yu.intro;

import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.StringTokenizer; 
import java.lang.*;

public class ExceptionalEmployeePay {	
	public static void main (final String [] args) {
		if (args.length != 1) {
			final String msg = " Usage : LoopyEmployeePayname_of_input file ";
			System.err.println(msg);
			throw new IllegalArgumentException(msg) ;
		}

		String inputFileName = null;
		File input = null;
		Scanner sc = null;
			
		try {
			inputFileName = args [0];
			input = new File (inputFileName) ;
			sc = new Scanner (input);
		}
		catch (FileNotFoundException e) {
  			System.err.println("error: file not found -- find a valid file." );
  			System.exit(0);
  		}
	
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
			String fullLine = sc.nextLine();
			StringTokenizer strings = new StringTokenizer(fullLine);
			int tokenAmount = strings.countTokens();
			
			if (tokenAmount != 4) {
			System.out.println("Problem at line # " + line + ": Expected 4 tokens per line, found " + tokenAmount + ". Discarding input & advancing");
			continue;
			}
			
			float Float = 0;
			int Integer = 0;
			String n = strings.nextToken();
			String h1 = strings.nextToken();
			Float h2 = null;
			String r1 = strings.nextToken();
			Float r2 = null;
			String c1 = strings.nextToken();
			Integer c2 = null;
			
			try {
				h2 = new Float(h1).floatValue();
			}
			catch (NumberFormatException e) {
				
				System.err.println("Problem at line # " + line + ": Could not parse Hrs Worked [" + h1 + "] into valid input" );
				continue;
			}
			try {
				r2 = new Float(r1).floatValue();
			}
			catch (NumberFormatException e) {
				System.err.println("Problem at line # " + line + ": Could not parse Wage Rate [" + r1 + "] into valid input" );
				continue;
			}
			try {
				c2 = new Integer(c1).intValue();
			}
			catch (NumberFormatException e) {
				System.err.println("Problem at line # " + line + ": Could not parse Deductions [" + c1 + "] into valid input" );
				continue;
			}
			
			
			float g = h2 * r2;
			float t = (.15f * g);
			float net = g - t - c2 - (.05f * g);				
			float a = net/h2;
			
			if (g > largestGross) {
			largestGrossId = n;
			largestGross = g;
			}
     		
			if (h2 < 1.0f) {
			System.out.println("Problem at line # " + line + ": ’hrs worked’ must be at least 1 -- but found " + h2);
			}
		
			if (r2 < 15.0f) {
			System.out.println("Problem at line # " + line + ": 'wage rate' must be at least 15 -- but found " + r2);
			}
		
			if ((c2 <= 0) || (c2 >= 35)) {
			System.out.println("Problem at line # " + line + ": deductions must be between 0 and 35 -- but found " + c2);
			}
		
			if (net < 0) {
			System.out.println("Problem at line # " + line + ": Net pay must be positive -- but found " + net);
			}
		
			if ((h2 >= 1.0f) && (r2 >= 15.0f) && (c2 > 0) && (c2 < 35) && (net >= 0)) {	
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
