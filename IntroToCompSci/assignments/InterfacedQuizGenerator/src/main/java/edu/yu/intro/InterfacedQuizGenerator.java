package	edu.yu.intro;

import java.io.*; 
import java.util.*; 
import java.lang.*; 

public class InterfacedQuizGenerator {
	public final static int N_QUIZ_QUESTIONS = 10;

	public static void main (final String [] args) {
		IntQuestion [] questions = new IntQuestion [10];
		createQuiz(questions);
		
		int [] userAnswers = new int [10];
		administerQuiz(questions, userAnswers);
		
		boolean [] answersResults = new boolean [10];
		gradeQuiz(questions, userAnswers, answersResults);
		
		int [] realAnswers = new int [10];
		printResults(questions, userAnswers, answersResults, realAnswers);
	}
	
	
	public static void createQuiz (IntQuestion[] questions) {
		for(int k = 0; k < 10; k++){
			final Random random = new Random();
			int randomNumber = random.nextInt(4);
			if ((randomNumber == 0) || (randomNumber == 2)) {
				questions[k] = new AdditionQuestion();
			}
			if ((randomNumber == 1) || (randomNumber == 3)) {
				questions[k] = new SubtractionQuestion();
			}
		}
	}	
	
	public static void administerQuiz (IntQuestion[] questions, int[] userAnswers) {
		Scanner input = new Scanner (System.in);
		for(int k = 0; k < 10; k++){
			String questionPrint = questions[k].getQuestion();
			int qNumber = k + 1;
			System.out.print("Question " + qNumber + ": " + questionPrint);
			int answer = input.nextInt();
			userAnswers[k] = answer;	
		}
	}

	public static void gradeQuiz (IntQuestion[] questions, int[] userAnswers, boolean[] answersResults) {
		for(int k = 0; k < 10; k++){
			int rightAnswer = questions[k].getCorrectAnswer();
			if (userAnswers[k] == rightAnswer) {
			answersResults[k] = true;
			}
			else {
			answersResults[k] = false;
			}
		}
	}
	
	public static void printResults (IntQuestion[] questions, int [] userAnswers, boolean [] answersResults, int [] realAnswers){
		System.out.println();
		System.out.println("Here are the correct answers:");
		for(int k = 0; k < 10; k++){
			realAnswers[k] = questions[k].getCorrectAnswer();
		}
		int correctQuestions = 0;
		int points = 0;
		for(int k = 0; k < 10; k++){
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