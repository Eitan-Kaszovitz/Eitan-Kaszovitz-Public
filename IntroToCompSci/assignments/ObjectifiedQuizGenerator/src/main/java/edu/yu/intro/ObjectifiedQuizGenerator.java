package	edu.yu.intro;

import java.util.Random;
import java.io.*; 
import java.util.*; 
import java.util.Scanner;

public class ObjectifiedQuizGenerator {
	public final static int N_QUIZ_QUESTIONS = 10;

	public static void main (final String [] args) {
		AdditionQuestion [] questions = new AdditionQuestion [N_QUIZ_QUESTIONS];
		createQuiz(questions);
		
		int [] userAnswers = new int [N_QUIZ_QUESTIONS];
		administerQuiz(questions, userAnswers);
		
		boolean [] answersResults = new boolean [N_QUIZ_QUESTIONS];
		gradeQuiz(questions, userAnswers, answersResults);
		
		int [] realAnswers = new int [N_QUIZ_QUESTIONS];
		printResults(questions, userAnswers, answersResults, realAnswers);
	}
	
	
	public static void createQuiz (AdditionQuestion[] questions) {
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			questions[k] = new AdditionQuestion();
		}
	}	
	
	public static void administerQuiz (AdditionQuestion[] questions, int[] userAnswers) {
		Scanner input = new Scanner (System.in);
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			String questionPrint = questions[k].getQuestion();
			int qNumber = k + 1;
			System.out.print("Question " + qNumber + ": " + questionPrint);
			int answer = input.nextInt();
			userAnswers[k] = answer;	
		}
	}

	public static void gradeQuiz (AdditionQuestion[] questions, int[] userAnswers, boolean[] answersResults) {
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			int rightAnswer = questions[k].getCorrectAnswer();
			if (userAnswers[k] == rightAnswer) {
			answersResults[k] = true;
			}
			else {
			answersResults[k] = false;
			}
		}
	}
	
	public static void printResults (AdditionQuestion[] questions, int [] userAnswers, boolean [] answersResults, int [] realAnswers){
		System.out.println();
		System.out.println("Here are the correct answers:");
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			realAnswers[k] = questions[k].getCorrectAnswer();
		}
		int correctQuestions = 0;
		int points = 0;
		for(int k = 0; k < N_QUIZ_QUESTIONS; k++){
			int qNumber = k + 1;
			String questionPrint = questions[k].getQuestion();
			System.out.print("Question " + qNumber + ": " + questionPrint + "Real Answer is " + realAnswers[k] + ". ");
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
		System.out.println("Your grade on the quiz is a " + points + ".");
	}
}