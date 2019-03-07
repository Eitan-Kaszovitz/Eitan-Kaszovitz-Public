
package	edu.yu.intro;

import java.util.Random;
import java.io.*; 
import java.util.*; 
import java.util.Scanner;

public class StaticQuizGenerator {
	public final static int N_QUIZ_QUESTIONS = 10;

	public static void main (final String [] args) {
		int [] firstNumbers = new int [N_QUIZ_QUESTIONS];
		int [] secondNumbers = new int [N_QUIZ_QUESTIONS];
		int [] userAnswers = new int [N_QUIZ_QUESTIONS];
		createQuiz(firstNumbers, secondNumbers); 
		administerQuiz(firstNumbers, secondNumbers, userAnswers);
		boolean [] answersResults = gradeQuiz(firstNumbers, secondNumbers, userAnswers);
		printResults(firstNumbers, secondNumbers, userAnswers, answersResults);
	}
	
	
	public static void createQuiz (int[] firstNumbers, int[] secondNumbers) {
		final Random random = new Random ();
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			firstNumbers[k] = random.nextInt(100) + 1;
			secondNumbers[k] = random.nextInt(100) + 1;
		}
	}	
	
	public static void administerQuiz (int[] firstNumbers, int[] secondNumbers, int[] userAnswers) {
		Scanner input = new Scanner (System.in);
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			int qNumber = k + 1;
			System.out.print("Question " + qNumber + ": What is " + firstNumbers[k] + " + " + secondNumbers[k] + "? ");
			int answer = input.nextInt();
			userAnswers[k] = answer;	
		}
	}

	public static boolean [] gradeQuiz (int [] firstNumbers, int [] secondNumbers, int [] userAnswers) {
		boolean [] answersResults = new boolean [N_QUIZ_QUESTIONS];
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			if (userAnswers[k] == (firstNumbers[k] + secondNumbers[k])) {
			answersResults[k] = true;
			}
			else {
			answersResults[k] = false;
			}
		}
		return answersResults;
	}
	
	public static void printResults (int [] firstNumbers, int [] secondNumbers, int [] userAnswers, boolean [] answersResults){
		int [] realAnswers = new int [N_QUIZ_QUESTIONS];
		System.out.println();
		System.out.println("Here are the correct answers:");
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			realAnswers[k] = (firstNumbers[k] + secondNumbers[k]);
		}
		int correctQuestions = 0;
		int points = 0;
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			int qNumber = k + 1;
			System.out.print("Question " + qNumber + ": " + firstNumbers[k] + " + " + secondNumbers[k] + " = " + realAnswers[k] + ". ");
			if (answersResults[k]){
			System.out.println("You were CORRECT.");
			correctQuestions++;
			points += 10;
			}
			else{
			System.out.println("You said " + userAnswers[k] + ", which is INCORRECT.");
			}
		}
		System.out.println();
		System.out.println("You got " + correctQuestions + " questions correct.");
		System.out.println("Your grade on the quiz is a " + points ".");
	}
}