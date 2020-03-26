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
 * @version 2/24/20 v4
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


    Game(){
        frame = new Window("Yahtzee",this);
        fileRead();
        turn = new Turn(number_of_dice, number_of_sides, rerollInput);
    }

    /**
     * Calls function to deal with dice configuration, then starts the turn
     */
    public void playGame(){


            if(turn.getScorecard().getUnusedScoresList().size() - 1 > 0) {
                System.out.println("starting hand");
                hand = turn.startHand();
                System.out.println("displaying in frame");
                turn.printHand();
                frame.displayHand(hand);
            } else {
                System.out.println("Final Score: ");
                turn.getScorecard().printScorecard();
                frame.finishGame();
            }

    }
    public void resetReRoll(){
        rerollInput = 2;
    }
    public void chooseScore(String choice){
        turn.chooseScore(choice);
    }

    public void scoreHand(){
        turn.scoreHand();
    }
    public void setKeptDice(int die,boolean kept){
        turn.setKeptDice(die,kept);
    }
    public ArrayList<Dice> rollHand() {
        if (rerollInput > 0) {
            rerollInput--;
            frame.decrementRollsLeft();
            return turn.reRoll();
        }

        return turn.getHand();
    }

    public ArrayList<Dice> sortHand(){
        turn.sortHand();
        return turn.getHand();
    }

    public ArrayList<String[]> getPossibleScores(){
        return turn.getPossibleScores();
    }

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