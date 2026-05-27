//Andre Aguilar
//25.05.2026
//AI Quiz Game: program reads questions from a .csv file
//& asks user to input their answer by typing a number between 1-4.
//displays if answer is wrong or right & shows total correct answers at the end

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Random;
import java.util.Scanner;

public class Main {

    //this line has the program know that there will be a total of 12 questions
    //ENHANCEMENT 1: added two more questions
    public static final int NUMBER_OF_QUESTIONS = 12;
    //this line has the program know that there will be a total of 4 choices to pick from for each question
    public static final int NUMBER_OF_CHOICES = 4;

    public static void main(String[] args) {
        String[] questions = new String[NUMBER_OF_QUESTIONS]; //stores questions one by one, extracted from each row in the .csv
        String[][] answers = new String[NUMBER_OF_QUESTIONS][NUMBER_OF_CHOICES]; //stores all options for related each question
        int[] correctAnswers = new int[NUMBER_OF_QUESTIONS]; //stores the correct answer for each question

        //this line allows the program to obtain the questions & answers
        readQuizFile(questions, answers, correctAnswers);

        //ENHANCEMENT 2: randomises question order
        shuffleQuiz(questions, answers, correctAnswers);

        //this scanner allows the user to input their answers
        Scanner input = new Scanner(System.in);
        //this has the user's score be set to zero at the start
        int score = 0;

        //both lines print a line in the output, welcoming you to the game & instructing you how to choose an answer
        System.out.println("Welcome to the AI Quiz Game!");
        System.out.println("Choose the correct answer by entering 1, 2, 3, or 4.\n");

        //the for loop goes through all questions and prints out the question number & the question from the .csv file
        for (int i = 0; i < questions.length; i++) {
            System.out.println("Question " + (i + 1) + ": " + questions[i]);

            //the for loop iterates answer choices for the corresponding question, printing all choices
            for (int j = 0; j < answers[i].length; j++) {
                System.out.println((j + 1) + ". " + answers[i][j]);
            }

            //prompts user to enter their answer
            System.out.print("Your answer: ");
            int userAnswer = input.nextInt() - 1; // takes the user's input & stores it inside the userAnswer variable

            //if user's answer is correct, this snippet will take action & will tell them they got it right
            if (userAnswer == correctAnswers[i]) {
                System.out.println("Correct!\n");
                score++;
            //if user's answer is incorrect, this snippet will take action & will let them know they were wrong
            } else {
                System.out.println("Incorrect.");
                System.out.println("The correct answer was: " + answers[i][correctAnswers[i]] + "\n");
            }
        }

        //once all questions are answered, the program will notify its completion & present the user with their final score
        System.out.println("Quiz complete!");
        System.out.println("Your final score is: " + score + " out of " + questions.length);

        //once all questions have been displayed & answered, & the final score has been shown,
        //the input box will close, unable to type inside the input
        input.close();
    }

    //this is the meaty portion of line 26 being able to read from the .csv file
    public static void readQuizFile(String[] questions, String[][] answers, int[] correctAnswers) {
        try {
            File file = new File("ai_quiz_questions.csv"); //this has the program scan for the .csv file's name in order to obtain the info when ran
            Scanner fileReader = new Scanner(file);

            fileReader.nextLine(); //skip header line if present

            int index = 0;

            // while there are questions, this snippet will read each line from the .csv file
            while (fileReader.hasNextLine() && index < questions.length) {
                String line = fileReader.nextLine();
                String[] data = line.split(",");

                questions[index] = data[0];

                //added this inside readQuizFile to help randomise the order of answer choices
                answers[index][0] = data[1];

                //changed the 0 to 1 in the "int i = 0;"
                for (int i = 1; i < NUMBER_OF_CHOICES; i++) {
                    answers[index][i] = data[i + 1];
                }

                // Assuming the correct answer is always the first choice (index 0)
                // You can modify this if your CSV contains the correct answer index explicitly
                correctAnswers[index] = 0;

                shuffleAnswers(answers[index], correctAnswers, index);
                index++;
            }

            fileReader.close();

        //the snippet will display only if said quiz file is not available to be found. program will not function as intended without it
        } catch (FileNotFoundException e) {
            System.out.println("The quiz file could not be found.");
        }
    }

    //the snippet behind enhancement 2
    //meaty stuff of line 29 being able to randomise the order of questions being asked
    public static void shuffleQuiz(String[] questions, String[][] answers, int[] correctAnswers) {
        Random rand = new Random();
        for (int i = questions.length - 1; i > 0; i--) {
            int j = rand.nextInt(i + 1);

            //swap questions
            String tempQuestion = questions[i];
            questions[i] = questions[j];
            questions[j] = tempQuestion;

            //swap answers
            String[] tempAnswers = answers[i];
            answers[i] = answers[j];
            answers[j] = tempAnswers;

            //swap correct answers
            int tempCorrect = correctAnswers[i];
            correctAnswers[i] = correctAnswers[j];
            correctAnswers[j] = tempCorrect;
        }
    }

    //ENHANCEMENT 3
    //this snippet has the answer choices be in a randomised order so the first choice is not always the correct one
    public static void shuffleAnswers(String[] answerChoices, int[] correctAnswers, int questionIndex) {
    Random rand = new Random();

    //hold original correct answer that is at index 0 before it gets shuffled like a deck of cards
    String correctAnswer = answerChoices[0];

    //shuffles the four answer choices
    for (int i = answerChoices.length - 1; i > 0; i--) {
        int j = rand.nextInt(i + 1);

        //swaps positions of two answer choices 
        String temp = answerChoices[i];
        answerChoices[i] = answerChoices[j];
        answerChoices[j] = temp;
    }

    //find new index of correct answer after shuffle
    for (int i = 0; i < answerChoices.length; i++) {
        if (answerChoices[i].equals(correctAnswer)) {
            correctAnswers[questionIndex] = i;
            break;
            }
        }
    }
}