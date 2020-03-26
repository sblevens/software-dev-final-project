/**
 * This program plays one turn of Yahtzee, plays a hand, asks to prints out possible scores and
 * asks to record the choosen score
 * CPSC 224
 * HW 4
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 2/24/20 v4
 */
import java.util.ArrayList;
import java.util.Scanner;

public class Turn {
    Scanner scan = new Scanner(System.in);

    private int number_of_dice;
    private int number_of_sides;
    private int rerollInput;
    private int reRoll;
    private Scorecard scorecard;

    private Hand hand;
    private ArrayList<Dice> theHand = new ArrayList<>();

    Turn(int number_of_dice, int number_of_sides,int rerollInput){
        this.number_of_dice = number_of_dice;
        this.number_of_sides = number_of_sides;
        this.rerollInput = rerollInput;
        System.out.println("New hand");
        hand = new Hand(number_of_dice, number_of_sides);
        Calculator calculator = new Calculator(hand.getHand(), number_of_dice, number_of_sides);
        scorecard = new Scorecard(number_of_sides,calculator);
        reRoll = rerollInput;
    }

    public void printHand(){
        hand.printHand();
    }

    /**
     * Goes through all actions of playing a hand throughout one turn,
     * including, rolling a hand, and rerolling, printing the hand, scoring the hand, and
     * asking if the player will play again
     */
    public ArrayList<Dice> startHand(){
        //play the turn
        boolean check = true;
        theHand = hand.newHand();
        return theHand;
    }

    public void scoreHand(){
        //print scores
        System.out.print("Final ");
        hand.printHand();

        //scorecard.printPossibleScores();
        //scorecard.chooseScore();

    }


    public void sortHand(){
        hand.sortHand();
    }

    public ArrayList<String[]> getPossibleScores(){
        return scorecard.printPossibleScores();
    }

    public void chooseScore(String choice){
        scorecard.chooseScore(choice);
    }

    public void printScorecard(){
        scorecard.printScorecard();
    }

    public ArrayList<Dice> getHand(){
        return hand.getHand();
    }
    /**
     * returns the scorecard object for the Game class to use
     * @return scorecard object
     */
    public Scorecard getScorecard(){
        return scorecard;
    }

    ArrayList<Dice> die;
    /**
     * Calls the Hand class method to reroll the dice, and then print the new hand
     */
    public ArrayList<Dice> reRoll(){
        die = hand.reRoll();
        hand.printHand();
        return die;
    }

    public void setKeptDice(int die,boolean kept){
        hand.setKeptDie(die,kept);
    }
}
