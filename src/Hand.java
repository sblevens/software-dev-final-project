/**
 * This program creates the hand of dice, determines which dice to reroll
 * CPSC 224
 * HW 4
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 3/26/20 v4
 */
import java.util.ArrayList;

public class Hand {
    ArrayList<Dice> hand;
    private int numberOfDice;
    private int numberOfSides;

    Hand(int numberOfDice, int numberOfSides){
        this.numberOfDice = numberOfDice;
        this.numberOfSides = numberOfSides;
        hand = new ArrayList<>();
    }

    /**
     * returns the arraylist of the hand of dice
     * @return hand the arraylist of the hand
     */
    public ArrayList<Dice> getHand(){
        return hand;
    }

    /**
     * Creates a new hand, ie ArrayList of Dice objects
     */
    public ArrayList<Dice> newHand(){
        clearHand();
        for (int i = 0; i < numberOfDice; i++) {
            hand.add(new Dice(numberOfSides));
            hand.get(i).setKept(false);
        }
        for(Dice d: hand){
            d.setSideUp();
        }
        return hand;
    }

    /**
     * Sorts the hand from smallest to largest integer
     */
    public void sortHand(){
        //run through a sorting algorithm
    boolean swap;
    Dice temp;

    do{
        swap = false;
        for(int count = 0; count < (hand.size()-1); count++){
            if(hand.get(count).getSideUp() > hand.get(count+1).getSideUp()){
                temp = hand.get(count);
                hand.remove(count);
                hand.add(count+1,temp);
                swap = true;
            }
        }
    } while(swap);
    }

    /**
     * Empties the ArrayList of the hand
     */
    public void clearHand(){
        hand.clear();
    }

    /**
     *  determines if string wants to keep or reroll a certain dice, then
     *  calls the dice class to reroll those dice, and then replaces the integer
     *  in the ArrayList
     *
     */
    public ArrayList<Dice> reRoll() {
        int location;
        for (location = 0; location < numberOfDice; location++) {
            if (!hand.get(location).getKept()) {
                hand.get(location).setSideUp();
            }
        }
        return hand;
    }


    /**
     * uses the die class to set the kept value of the die
     * @param die index of die
     * @param kept true or false, whether the die is kept or not
     */
    public void setKeptDie(int die, boolean kept){
        hand.get(die-1).setKept(kept);
    }

}

