package	edu.yu.intro;

import java.util.Random;

public class SubtractionQuestion implements IntQuestion {
	private int firstNumber;
	private int secondNumber;
	
	public SubtractionQuestion () { 
		final Random random = new Random ();
		firstNumber = random.nextInt(100) + 1;
		secondNumber = random.nextInt(100) + 1;
	}

	public String getQuestion () {
	if (secondNumber > firstNumber) {
		firstNumber = secondNumber;
		secondNumber = firstNumber;
	}
	return "What is " + firstNumber + " - " + secondNumber + "? ";
	}
	
	public int getCorrectAnswer () {
	int rightAnswer = firstNumber - secondNumber;
	return rightAnswer;
	}
}