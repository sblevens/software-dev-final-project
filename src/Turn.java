/**
 * This program plays one turn of Yahtzee, plays a hand, asks to prints out possible scores and
 * asks to record the choosen score
 * CPSC 224
 * HW 3
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 2/24/20 v3
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

    Hand hand;

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



    /**
     * Goes through all actions of playing a hand throughout one turn,
     * including, rolling a hand, and rerolling, printing the hand, scoring the hand, and
     * asking if the player will play again
     */
    public void playHand(){
        //play the turn
        boolean check = true;
        //do {

            //check to play again
        while(check) {
            System.out.println("y to play the hand, S to look at scorecard: ");
            String endInput = scan.nextLine();
            if (endInput.equals("S")) {
                scorecard.printScorecard();
                check = false;
            } else if (endInput.equals("y")) {
                System.out.println("playing hand");
                check = false;
            } else {
                System.out.println("wrong input. try again.");
                check = true;
            }
        }
        hand.newHand();
        hand.printHand();

        checkKeep();

        hand.sortHand();

        //print scores
        System.out.print("Final ");
        hand.printHand();
        //hand.scoreHand();
        scorecard.printPossibleScores();
        scorecard.chooseScore();

        hand.clearHand();

    }

    /**
     * returns the scorecard object for the Game class to use
     * @return scorecard object
     */
    public Scorecard getScorecard(){
        return scorecard;
    }

    /**
     * Checks to see if the input is keeping all dice
     * if not, calls a reroll function
     */
    public void checkKeep(){
        String input;
        boolean end = false;
        reRoll = rerollInput;
        newRoll:
            while (reRoll > 0 && !end) {
                System.out.println("Enter y/n to keep or re-roll: ");
                input = scan.nextLine();
                System.out.println(input);

                //check if correct input
                if(!(input.length() == number_of_dice)){
                    System.out.println("wrong number, try again");
                    continue newRoll;
                }

                //Check if keeping the die
                boolean checkYes = true;
                int count = 0;
                while(checkYes && count<number_of_dice){
                    if(input.charAt(count)==('y')){
                        count++;
                    }else if(input.charAt(count)==('n')){
                        checkYes = false;
                    }else{
                        System.out.println("wrong characters, try again");
                        continue newRoll;
                    }
                }
                end = checkYes;
                //re roll if not keeping all die
                if (!end) {
                    reRoll(input);
                }
                reRoll--;
            }
    }

    /**
     * Calls the Hand class method to reroll the dice, and then print the new hand
     * @param input the string of what dice to keep and which to reroll
     */
    public void reRoll(String input){
        hand.reRoll(input);
        hand.printHand();
    }

}
