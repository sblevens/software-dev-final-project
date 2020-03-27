import javax.swing.*;
import java.awt.*;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program plays a full game of Yahtzee, and sets the dice configuration
 * CPSC 224
 * HW 4
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 3/26/20 v4
 */

public class Game {
    private int number_of_sides;
    private int number_of_dice;
    private int rerollInput = 2;
    Scanner scan = new Scanner(System.in);
    private Window frame;
    private JFrame configFrame;
    private ArrayList<Dice> hand = new ArrayList<>();
    private Turn turn;
    private boolean firstTurn = true;


    Game(){
        frame = new Window("Yahtzee",this);
        fileRead();
    }

    /**
     * Calls function to deal with dice configuration, then starts the turn
     */
    public void playGame(){
        if(firstTurn){
            turn = new Turn(number_of_dice, number_of_sides, rerollInput);
            firstTurn = false;
        }
            if(turn.getScorecard().getUnusedScoresList().size() - 1 > 0) {
                //System.out.println("starting hand");
                hand = turn.startHand();
                //System.out.println("displaying in frame");
                //turn.printHand();
                frame.displayHand(hand);
            } else {
                //System.out.println("Final Score: ");
                turn.getScorecard().printScorecard();
                frame.finishGame();
            }

    }

    /**
     * resets the number of rolls left for when a new turn is starting
     */
    public void resetReRoll(){
        rerollInput = 2;
    }

    /**
     * calls the turn class to select the section for where to place the score
     * @param choice the section for what score is chosen to place on the scorecard
     */
    public void chooseScore(String choice){
        turn.chooseScore(choice);
    }

    public ArrayList<Dice> getHand(){
        return turn.getHand();
    }


    /**
     * sets the die to kept or not
     * @param die the index of the die to set
     * @param kept true or false for whether the die is set to kept or not kept
     */
    public void setKeptDice(int die,boolean kept){
        turn.setKeptDice(die,kept);
    }

    /**
     * calls the turn class to roll the hand, and figures out if there are rolls left
     * @return hand the hand, after a roll if there are rolls left, or returns the same hand if no rolls are left
     */
    public ArrayList<Dice> rollHand() {
        if (rerollInput > 0) {
            rerollInput--;
            frame.decrementRollsLeft();
            return turn.reRoll();
        }

        return turn.getHand();
    }

    /**
     * calls the turn class to sort the hand
     * @return hand the sorted hand of dice
     */
    public ArrayList<Dice> sortHand(){
        turn.sortHand();
        return turn.getHand();
    }

    /**
     * calls the turn class to get the arraylist of possible scores, and gives them to the window class
     * @return ususedScoresList the arraylist of the unused scores
     */
    public ArrayList<String[]> getPossibleScores(){
        return turn.getPossibleScores();
    }

    /**
     * Calls the turn class to print the scorecard
     */
    public void printScorecard(){
        turn.printScorecard();
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

        } catch (FileNotFoundException e) {
            System.out.println("file not found");
            System.exit(1);
        }

        frame.setDisplaySideLabel(number_of_sides);
        frame.setDisplayDiceLabel((number_of_dice));
    }
}