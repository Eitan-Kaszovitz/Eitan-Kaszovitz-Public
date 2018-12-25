package	edu.yu.intro;

import java.util.Random;

public class AdditionQuestion implements IntQuestion {
	private int firstNumber;
	private int secondNumber;
	
	public AdditionQuestion () { 
		final Random random = new Random ();
		firstNumber = random.nextInt(100) + 1;
		secondNumber = random.nextInt(100) + 1;
	}

	public String getQuestion () {
	return "What is " + firstNumber + " + " + secondNumber + "? ";
	}
	
	public int getCorrectAnswer () {
	int rightAnswer = firstNumber + secondNumber;
	return rightAnswer;
	}
}