/**
 * This program plays one turn of Yahtzee, plays a hand, calculates possible scores and
 * records the choosen score
 * CPSC 224
 * HW 4
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 3/26/20 v4
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
        //System.out.println("New hand");
        hand = new Hand(number_of_dice, number_of_sides);
        Calculator calculator = new Calculator(hand.getHand(), number_of_dice, number_of_sides);
        scorecard = new Scorecard(number_of_sides,calculator);
        reRoll = rerollInput;
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

    /**
     * calls on the hand class to sort the hand
     */
    public void sortHand(){
        hand.sortHand();
    }

    /**
     * gets and returns the arraylist of possible score options
     * @return possible scores ; the arraylist of possible score options
     */
    public ArrayList<String[]> getPossibleScores(){
        return scorecard.printPossibleScores();
    }

    /**
     * calls on the scorecard to record the choice of score
     * @param choice string of the section that the score will be placed in
     */
    public void chooseScore(String choice){
        scorecard.chooseScore(choice);
    }

    /**
     * calls on the scorecard class to set the scorcard
     */
    public void printScorecard(){
        scorecard.printScorecard();
    }

    /**
     * gets the hand from the hand class, and returns it
     * @return hand the arraylist of the hand
     */
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
        //hand.printHand();
        return die;
    }

    /**
     * calls on the hand class to set the kept value of the die
     * @param die index of the die value
     * @param kept true or false value of whether or not the dice is kept
     */
    public void setKeptDice(int die,boolean kept){
        hand.setKeptDie(die,kept);
    }
}
