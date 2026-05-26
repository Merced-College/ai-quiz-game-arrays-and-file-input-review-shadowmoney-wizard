//Andre Aguilar
//25.05.2026
//AI Quiz Game: program reads questions from a .csv file
//& asks user to input their answer by typing a number between 1-4.
//displays if answer is wrong or right & shows total correct answers at the end

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Main {


    //this line has the program know that there will be a total of 10 questions, nothing more, nothing less
    public static final int NUMBER_OF_QUESTIONS = 10;
    //this line has the program know that there will be a total of 4 choices to pick from for each question
    public static final int NUMBER_OF_CHOICES = 4;

    public static void main(String[] args) {
        String[] questions = new String[NUMBER_OF_QUESTIONS]; //stores questions one by one, extracted from each row in the .csv
        String[][] answers = new String[NUMBER_OF_QUESTIONS][NUMBER_OF_CHOICES]; //stores all options for related each question
        int[] correctAnswers = new int[NUMBER_OF_QUESTIONS]; //stores the correct answer for each question


        //this line allows the program to obatain the questions & answers
        readQuizFile(questions, answers, correctAnswers);


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

            //the for loops iterates answer choices for the corresponding question, printing all choices
            for (int j = 0; j < answers[i].length; j++) {
                System.out.println((j + 1) + ". " + answers[i][j]);
            }

            //prompts the user to enter their answer
            System.out.print("Your answer: ");
            int userAnswer = input.nextInt() - 1; //takes the user's input & stores it inside the userAnswer variable


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

            fileReader.nextLine();

            int index = 0;


            //while there are questions, this snippet will read each line from the .csv file
            while (fileReader.hasNextLine() && index < questions.length) {
                String line = fileReader.nextLine();
                String[] data = line.split(",");

                questions[index] = data[0];

                for (int i = 0; i < NUMBER_OF_CHOICES; i++) {
                    answers[index][i] = data[i + 1];
                }

                correctAnswers[index] = 0;
                index++;
            }

            fileReader.close();

        //the snippet will display only if said quiz file is not available to be found. program will not function as intended without it
        } catch (FileNotFoundException e) {
            System.out.println("The quiz file could not be found.");
        }
    }
}