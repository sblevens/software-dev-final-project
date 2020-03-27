/**
 * This program rolls the dice for the Yahtzee game
 * CPSC 224
 * HW 4
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 3/26/20 v4
 */
import java.util.Random;

public class Dice {
    Random rand = new Random();

    private int totalSides;
    private int sideUp;
    private boolean isKept;

    Dice(int sides){
        totalSides = sides;
    }

    //Setters
    /**
     * Rolls the dice, gets a random integer
     */
    public void setSideUp(){
        sideUp = rand.nextInt(totalSides)+1;
    }

    //Getters
    /**
     * returns the side up on the current dice
     *
     * @return the sideUp on the dice
     */
    public int getSideUp(){
        return sideUp;
    }

    /**
     * sets the value of whether or not the dice is kept
     * @param kept true or false, whether the dice is being kept or not
     */
    public void setKept(boolean kept){
        isKept = kept;
    }

    /**
     * returns true or false, if the dice is being kept or not during the next roll
     * @return isKept
     */
    public boolean getKept(){
        return isKept;
    }
}
