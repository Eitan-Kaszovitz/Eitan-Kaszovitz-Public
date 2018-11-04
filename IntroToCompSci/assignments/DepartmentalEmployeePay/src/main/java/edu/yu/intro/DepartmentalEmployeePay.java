
package	edu.yu.intro;

import java.util.Scanner;
import java.io.*;
import java.io.FileNotFoundException;
import java.util.StringTokenizer; 
import java.lang.*;

public class DepartmentalEmployeePay {	
	public static void main (final String [] args) {
		if (args.length != 2) {
			final String msg = " Usage : LoopyEmployeePayname_of_input file ";
			System.err.println(msg);
			throw new IllegalArgumentException(msg) ;
		}

		// file setup 
		String deptInputFile = null;
		String employeeInputFile = null;
		File deptInput = null;
		Scanner sc1 = null;
		File employeeInput = null;
		Scanner sc2 = null;
		
		try {
			deptInputFile = args [0];
			employeeInputFile = args [1];
			deptInput = new File (deptInputFile) ;
			sc1 = new Scanner (deptInput);
			employeeInput = new File (employeeInputFile) ;
			sc2 = new Scanner (employeeInput);
			
		}
		catch (FileNotFoundException e) {
  			System.err.println("error: file not found -- enter a valid file." );
  			System.exit(0);
  		}
  		//////
	
		System.out.println("****************************************************************************************************");

	
		//getting array of departments
		String[] departmentArray = new String [10];
		int i = 0;
		int line1 = 0;
		String name = "none";
		boolean noMatch = true;
  		
  		try {
  		 	while (sc1.hasNext()) {
  				noMatch = true;
  				name = sc1.next();
  				for(int k = 0; k < i; k++){
        			if (name.equals(departmentArray[k])){
            			System.out.println("error on department list: " + departmentArray[k] + " department entered multiple times. Discarding input & advancing");
            			noMatch = false;
            			break;
            		}
            	}
             	if (noMatch) {
            	 	departmentArray[i] = name;
           	 		i++;
            	}
            }		
  		}
  		catch (ArrayIndexOutOfBoundsException e) {
  		System.err.println("error on department list: list can't exceed 10 departments");
  		System.exit(0);
  		}
		/////////
		
		
		//getting arrays of values
		int[] empArray = new int [10];
		double[] totalGrossArray = new double [10];
		double[] avgGrossArray = new double [10];
		////////


		//declaring variables for employee while loop
		int line2 = 0;
		int employeeNumber = 0;
		boolean deptIssue = true;
		/////////////
		
		//employee while loop
		outerwhile:
		while (sc2.hasNextLine()) {
			deptIssue = true;
			line2++;
			String fullLine = sc2.nextLine();
			StringTokenizer strings = new StringTokenizer(fullLine);
			int tokenAmount = strings.countTokens();
			
			if (tokenAmount != 5) {
			System.out.println("Problem at employee line # " + line2 + ": Expected 5 tokens per line, found " + tokenAmount + ". Discarding input & advancing");
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
			String deptName = strings.nextToken();
			
			//match a listed department name
			for(int k1 = 0; k1 < departmentArray.length; k1++){
        		if (deptName.equals(departmentArray[k1])){
        			deptIssue = false;
        			break;
            	}
            }
            /////////////
            
            if (deptIssue) {            		
            	System.out.println("Problem with employee " + n + ": No valid Department. Discarding input & advancing");
            	continue;
            }
            
			
			try {
				h2 = new Float(h1).floatValue();
			}
			catch (NumberFormatException e) {
				
				System.err.println("Problem with employee " + n + ": Could not parse Hrs Worked [" + h1 + "] into valid input. Discarding input & advancing" );
				continue;
			}
			try {
				r2 = new Float(r1).floatValue();
			}
			catch (NumberFormatException e) {
				System.err.println("Problem with employee " + n + ": Could not parse Wage Rate [" + r1 + "] into valid input. Discarding input & advancing" );
				continue;
			}
			try {
				c2 = new Integer(c1).intValue();
			}
			catch (NumberFormatException e) {
				System.err.println("Problem at employee " + n + ": Could not parse Deductions [" + c1 + "] into valid input. Discarding input & advancing" );
				continue;
			}
			
			
			float g = h2 * r2;
			float t = (.15f * g);
			float net = g - t - c2 - (.05f * g);				
			float a = net/h2;
     		
			if (h2 < 1.0f) {
			System.out.println("Problem with employee " + n + ": ’hrs worked’ must be at least 1 -- but found " + h2 + ". Discarding input & advancing");
			}
		
			if (r2 < 15.0f) {
			System.out.println("Problem with employee " + n + ": 'wage rate' must be at least 15 -- but found " + r2 + ". Discarding input & advancing");
			}
		
			if ((c2 <= 0) || (c2 >= 35)) {
			System.out.println("Problem with employee " + n + ": deductions must be between 0 and 35 -- but found " + c2 + ". Discarding input & advancing");
			}
		
			if (net < 0) {
			System.out.println("Problem with employee " + n + ": Net pay must be positive -- but found " + net + ". Discarding input & advancing");
			}
			
			if ((h2 >= 1.0f) && (r2 >= 15.0f) && (c2 > 0) && (c2 < 35) && (net >= 0)) {	
				for(int x = 0; x < i; x++){
        			if (deptName.equals(departmentArray[x])){
           				empArray[x]++;
						totalGrossArray[x]+= g;
            		}
            	}
			}
			
		}
		
		System.out.printf("%25s", "Department");
        System.out.printf("%25s", "# Employees");
        System.out.printf("%25s", "Total Gross Pay");
        System.out.printf("%25s%n", "Average Gross Pay");
			
		i--;
		
		outerloop:
		for(int x2 = 0; x2 < i; x2++){
            /*for(int k = 0; k < x2; k++){
        		if (departmentArray[x2].equals(departmentArray[k])){
            		continue outerloop;
            	}
            }*/
            avgGrossArray[x2] = ((totalGrossArray[x2])/(empArray[x2]));
            System.out.printf("%25s", departmentArray[x2]);
            System.out.printf("%25d", empArray[x2]);
            System.out.printf("%25.2f", totalGrossArray[x2]);
            System.out.printf("%25.2f%n", avgGrossArray[x2]);
        } 
		
		System.out.println("****************************************************************************************************");
		
	}
}
