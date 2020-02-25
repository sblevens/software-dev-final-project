import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

/**
 * This program plays a full game of Yahtzee, and sets the dice configuration
 * CPSC 224
 * HW 3
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 2/24/20 v3
 */

public class Game {
    private static int number_of_sides;
    private static int number_of_dice;
    private static int rerollInput;
    Scanner scan = new Scanner(System.in);

    Game(){
        System.out.println("Welcome to Yahtzee!\n");
    }

    /**
     * Calls function to deal with dice configuration, then starts the turn
     */
    public void playGame(){
        boolean again = false;
        boolean correct;
        String input;
        do {
            fileRead();
            Turn turn = new Turn(number_of_dice, number_of_sides, rerollInput);

            while (turn.getScorecard().getUnusedScoresList().size() - 1 > 0) {
                turn.playHand();
            }
            System.out.println("Final Score: ");
            turn.getScorecard().printScorecard();

            correct = false;
            while(!correct) {
                System.out.println("Play again? y/n");
                input = scan.nextLine();
                if (input.equals("y")) {
                    again = true;
                    correct = true;
                } else if (input.equals("n")) {
                    System.out.println("Ending game.");
                    again = false;
                    correct = true;
                } else {
                    System.out.println("wrong input. try again.");
                    correct = false;
                }
            }
        }while(again);

    }

    /**
     * opens and reads the input file for the number of dice, sides on the dice, and total reRolls allowed
     */
    public void fileRead() {
        Scanner scan = new Scanner(System.in);
        Scanner inFile = null;
        File text = new File("yahtzeeConfig.txt");
        int[] settings = new int[3];
        int i = 0;
        try {
            inFile = new Scanner(text);
            while (inFile.hasNextInt()) {
                settings[i] = inFile.nextInt();
                i++;
            }
            number_of_sides = settings[0];
            number_of_dice = settings[1];
            rerollInput = (settings[2] - 1);

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(1);
        }

        System.out.println("you are playing with " + number_of_dice + " " + number_of_sides +
                "-sided dice");
        System.out.println("you get " + (rerollInput+1)  + " rolls per hand\n");
        System.out.println("enter 'y' if you would like to change the configuration ");
        String change = scan.nextLine();
        if(change.equals("y")){
            try{
                PrintStream outFile = new PrintStream("yahtzeeConfig.txt");

                System.out.println("enter the number of sides on each die ");
                number_of_sides = scan.nextInt();
                outFile.println(number_of_sides);

                System.out.println("enter the number of dice in play ");
                number_of_dice = scan.nextInt();
                outFile.println(number_of_dice);

                System.out.println("enter the number of rolls per hand ");
                rerollInput = scan.nextInt() - 1;
                outFile.println(rerollInput+1);
            }
            catch (FileNotFoundException e){
                System.out.println("file not found");
                System.exit(1);
            }
        } else {
            System.out.println("Starting game!");
        }

    }
}