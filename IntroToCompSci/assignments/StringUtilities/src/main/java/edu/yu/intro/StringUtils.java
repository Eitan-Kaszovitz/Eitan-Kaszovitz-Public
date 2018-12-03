
package edu.yu.intro;

import java.util.Scanner;
import java.io.*;
import java.lang.*;

public class StringUtils {

	public static void main (String[] args) {
	Scanner input = new Scanner(System.in);
	System.out.print("Enter a word to test for palindrome: ");
	String name = input.nextLine();
	boolean palindromeStatus = isPalindrome(name);
	System.out.println("Palindrome Status: " + palindromeStatus);
	
	System.out.println();
	System.out.print("Enter a file name for extension: ");
	String filename = input.nextLine();
	String extension = extractExtension(filename);
	System.out.println("Extension: " + extension);
	
	System.out.println();
	System.out.print("Enter a string for length status: ");
	String str = input.nextLine();
	boolean lengthStatus = isNotEmpty(str);
	System.out.println("Length Status: " + lengthStatus);
	
	System.out.println();
	System.out.print("Enter a string for blank status: ");
	String str2 = input.nextLine();
	boolean blankStatus = isBlank(str2);
	System.out.println("Blank Status: " + blankStatus);
	
	System.out.println();
	System.out.print("Enter two strings for matching: ");
	String matcher1 = input.nextLine();
	String matcher2 = input.nextLine();
	boolean matchStatus = equals(matcher1, matcher2);
	System.out.println("Match Status: " + matchStatus);
	
	System.out.println();
	System.out.print("Enter to join: ");
	String enter1 = "hi";
	String enter2 = "    ";
	String enter3 = "john";
	String[] objectArray = new String[] {enter1, enter2, enter3};
	String joinedArray = join(objectArray);
	System.out.println("Joined Version: " + joinedArray);
	
	System.out.println();
	System.out.print("Enter a word/number to be left padded: ");
	String paddedString = input.next();
	int paddedNumber = input.nextInt();
	String padded = leftPad(paddedString, paddedNumber);
	System.out.println(padded);
	
	System.out.println();
	System.out.println("Enter a word and a letter to be removed:");
	String fullString = input.next();
	char removedLetter = input.next().charAt(0);
	String removedVersion = remove(fullString, removedLetter);
	System.out.println(removedVersion);
	
	}
	public static boolean isPalindrome (final String s) {
		if (s == null) {
			throw new IllegalArgumentException();
		}
		String noSpaces = s.replaceAll("\\s", "").toString();
		StringBuilder sb = new StringBuilder(noSpaces);
		String reverseString = sb.reverse().toString();
		boolean status = false;
		if (reverseString.equals(noSpaces)) {
			status = true;
		}
		return status;
	}

	public static String extractExtension (final String filename) {
		String noSpace = filename.replaceAll("\\s", "").toString();
		int i = noSpace.lastIndexOf(".");
		int firstDot = noSpace.indexOf(".");
		int secondDot = noSpace.indexOf(".", firstDot + 1);
		if (secondDot != -1) {
			throw new RuntimeException();
		}
		
		if ((noSpace.indexOf(".")) == -1) {
			return "";
		}
		else {
			return noSpace.substring(i + 1);
		}
	}
	
	public static boolean isNotEmpty (final String str) {
		boolean length = true;
		try {
			if (str.length() == 0) {
			 	length = false;
			}
			return length;
		}
	 	catch (NullPointerException e) {
	 		return false;
	 	}
	}

	public static boolean isBlank (final String str) {
		try {
			String spaceRemover = str.replaceAll("\\s", "").toString();
			boolean length = false;
		
			if (spaceRemover.length() == 0) {
				length = true;
			}
	 		return length;
	 	}
	 	catch (NullPointerException e) {
	 		return true;
	 	}
	}

	public static boolean equals (final String str1, final String str2) {
		try {
			boolean equals = false;
			if (str1.equals(str2)) {
				equals = true;
			}
			return equals;
		}
		catch (NullPointerException e) {
			if ((str1 == null) && (str2 == null)) {
				return true;
			}
			else {
				return false;
			}
		}
	}
	
	public static String join (Object[] array) {
		if (array == null) {
			throw new RuntimeException();
		}
		String[] stringArray = new String[array.length];
		String stringVersion = null;
		for (int x = 0; x < array.length; x++) {
			if (array[x] == null) {
				array[x] = "   ";		
			}
			stringVersion = array[x].toString().replaceAll("\\s", "").toString();
			stringArray[x] = stringVersion;	
		}
		String joinedArray = String.join("", stringArray);
		return joinedArray;
	}
	
	public static String leftPad (final String str, int len) {
		if (len > 20) {
			len = 20;
		}
		if (str == null) {
			return null;
		}
		else {
			return String.format("%1$" + len + "s", str);
		}
	}
	
	public static String trimWhitespaceTillEmpty (final String str) {
		try {
			String trimmedVersion = str.trim();
			if (trimmedVersion == "") {
				return "";
			}
			else {
				return trimmedVersion;
			}
		}
		catch (NullPointerException e) {
	 		return "";
	 	}
	}
	
	public static String remove (final String str, final char removeIt) {
		try {
			String s = String.valueOf(removeIt);
			String removedChar = str.replaceAll(s, "").toString();
			return removedChar;
		}
		catch (NullPointerException e) {
	 		return null;
	 	}
	}
}





