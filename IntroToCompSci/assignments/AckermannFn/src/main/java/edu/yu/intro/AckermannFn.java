
package	edu.yu.intro;

import java.util.Scanner;
import java.io.*; 
import java.util.*;
import java.lang.IllegalArgumentException;

public class AckermannFn {
	public static void main (final String [] args) {
		Scanner input = new Scanner (System.in);
		System.out.print("Enter ‘x and Y‘ -- I will calculate ackermann's method: ");
		long a = input.nextLong();
		long b = input.nextLong(); 
		long ackermannValue = ackermann(a, b);
		System.out.println(ackermannValue);
	}
	public static long ackermann(long m, long n) {
		long yValue = 0;
		if (m == 0) {
			yValue = (n + 1);
			return yValue;
		}
		else if (n == 0) {
			return ackermann((m - 1), 1);
		}
		else {
			return ackermann ((m - 1), (ackermann(m, (n - 1))));
		}
		
	}
}