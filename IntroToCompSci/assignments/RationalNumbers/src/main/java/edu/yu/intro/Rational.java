
package	edu.yu.intro;

import java.lang.IllegalArgumentException;
import java.lang.UnsupportedOperationException;


public class Rational {
	///instance variables////
	private int numerator;
	private int denominator;
	////////
	
	////constructors
	public Rational(int num, int denom){
		if (denom == 0){
			throw new IllegalArgumentException();
		}
		numerator = num;
		denominator = denom;
	}
	
	public Rational(){
		numerator = 0;
		denominator = 1;
	}
	////////	
	
	public String printRational(){
		String numberValue;
		double doubleNum = (double) numerator;
		double rationalNumber = (doubleNum/denominator);
		if (rationalNumber < 0){
			numberValue = "Negative";
		}
		else {
			numberValue = "Positive";
		}
		return "Numerator: " + numerator + ". Denominator: " + denominator + ". Positve/Negative: " + numberValue;
	}
	
	public int getNumerator(){
		return numerator;
	}
	
	public int getDenominator(){
		return denominator;
	}
	
	public void negate(){
		numerator = numerator * (-1);
	}
	
	public void invert(){
		int oldNumerator = numerator;
		if (oldNumerator == 0) {
			throw new UnsupportedOperationException();
		}
		numerator = denominator;
		denominator = oldNumerator;
	}
	
	public double toDouble (){
		double doubleNumerator = (double) numerator;
		double doubleDenominator = (double) denominator;
		return doubleNumerator/doubleDenominator;
	}
	
	public Rational reduce() {	
		
		int firstNumber = numerator;
		int secondNumber = denominator;
		
		if (firstNumber < secondNumber) {
			firstNumber = denominator;
			secondNumber = numerator;
		}
		int r = firstNumber % secondNumber;
		while (r != 0) {
			firstNumber = secondNumber;
			secondNumber = r;
			r = firstNumber % secondNumber;
		}
		 int gcd = secondNumber;
		 int simpleNum = (numerator / gcd);
		 int simpleDen = (denominator / gcd);
		Rational simpleRational = new Rational (simpleNum, simpleDen);
		return simpleRational;
		
		
	}
	
	
	
	
	public Rational add(final Rational that){
		Rational thatRational = that;
		int thatNumerator = thatRational.getNumerator();
		int thatDenominator = thatRational.getDenominator();
		int thisNumerator = this.numerator;
		int thisDenominator = this.denominator;
		int commonDenominator = (thatDenominator * thisDenominator);
		int multiplyingNumThatNumber = (commonDenominator / thatDenominator);
		int multiplyingNumThisNumber = (commonDenominator / thisDenominator);
		int newThatNumerator = (thatNumerator * multiplyingNumThatNumber);
		int newThisNumerator = (thisNumerator * multiplyingNumThisNumber);
		int addedNumerator = (newThatNumerator + newThisNumerator);
		
		Rational addedRational = new Rational (addedNumerator, commonDenominator);
		
		
		Rational simpleAddedRational = addedRational.reduce();
		
		return simpleAddedRational;
	} 
	
	public static void main (String[] args) {
		Rational myRational = new Rational(9, 5);
		String status = myRational.printRational();
		System.out.println(status);
		
		myRational.negate();
		status = myRational.printRational();
		System.out.println(status);
		
		myRational.invert();
		status = myRational.printRational();
		System.out.println(status);
		
		System.out.println("rational value: " + myRational.toDouble());
		
		Rational simpleRational = myRational.reduce();
		String simpleStatus = simpleRational.printRational();
		System.out.println(simpleStatus);
		
		Rational thatRational = new Rational(2, 2);
		myRational = myRational.add(thatRational);
		String addedStatus = myRational.printRational();
		System.out.println(addedStatus);
	}


}