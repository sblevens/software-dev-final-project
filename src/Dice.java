/**
 * This program rolls the dice for the Yahtzee game
 * CPSC 224
 * HW 4
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 2/24/20 v4
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

    public void setKept(boolean kept){
        isKept = kept;
    }

    public boolean getKept(){
        return isKept;
    }
}
