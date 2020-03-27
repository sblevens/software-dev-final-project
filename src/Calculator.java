/**
 * This program calculates the possible scores for Yahtzee
 * CPSC 224
 * HW 4
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 3/26/20 v4
 */
import java.util.ArrayList;

public class Calculator {
    private ArrayList<Dice> finalRoll;
    private int numOfDice;
    private int numOfSides;

    Calculator(ArrayList<Dice> roll, int numOfDice, int numOfSides){
        finalRoll = roll;
        this.numOfDice = numOfDice;
        this.numOfSides = numOfSides;
    }

    /**
     * Totals the values of every die in the hand
     * @return total: the total values of every die added up
     */
    public int totalAllDice()
    //this function returns the total value of all dice in a hand
    {
        int total = 0;
        for (int diePosition = 0; diePosition < numOfDice; diePosition++)
        {
            total += finalRoll.get(diePosition).getSideUp();
        }
        return total;
    }

    /**
     * Counts the number of die with the target dieValue
     * @param num the target dieValue
     * @return currentCount : the number of die with the target dieValue
     */
    public int count(String num){
        int currentCount = 0;
        int dieValue = Integer.parseInt(num);
        for (int diePosition = 0; diePosition < numOfDice; diePosition++) {
            if (finalRoll.get(diePosition).getSideUp() == dieValue)
                currentCount++;
        }
        return currentCount;
    }

    /**
     * Finds the count of the die value occurring most in the hand
     * @return maxCount: the total amount of dice that have the same value.
     */
    int maxOfAKindFound()
    //this function returns the count of the die value occurring most in the hand
    //but not the value itself
    {
        int maxCount = 0;
        int currentCount;
        for (int dieValue = 1; dieValue <= numOfSides; dieValue++) {
            currentCount = 0;
            for (int diePosition = 0; diePosition < numOfDice; diePosition++) {
                if (finalRoll.get(diePosition).getSideUp() == dieValue)
                    currentCount++;
            }
            if (currentCount > maxCount)
                maxCount = currentCount;
        }
        return maxCount;
    }

    /**
     * Finds the length of the longest straight found
     * @return maxLength: the length of the longest straight found
     */
    int maxStraightFound()
    //this function returns the length of the longest
    //straight found in a hand
    {
        int maxLength = 1;
        int curLength = 1;
        for(int counter = 0; counter < numOfDice-1; counter++)
        {
            if (finalRoll.get(counter).getSideUp() + 1 == finalRoll.get(counter + 1).getSideUp() ) //jump of 1
                curLength++;
            else if(numOfDice>2) {
                if(finalRoll.get(counter).getSideUp() + 1 < finalRoll.get(counter + 1).getSideUp()) //jump of >= 2
                    curLength = 1;
            }
            if (curLength > maxLength)
                maxLength = curLength;
        }
        return maxLength;
    }

    /**
     * Determines if a Full House is found
     * @return foundFH: boolean value for if a full house was found or not
     */
    boolean fullHouseFound()
    //this function returns true if the hand is a full house
    //or false if it does not
    {
        boolean foundFH = false;
        boolean found3K = false;
        boolean found2K = false;
        boolean found5K = false;
        int value3K = -1;
        int currentCount ;
        for (int dieValue = 1; dieValue <=numOfSides; dieValue++)
        {
            currentCount = 0;
            for (int diePosition = 0; diePosition < numOfDice; diePosition++)
            {
                if (finalRoll.get(diePosition).getSideUp() == dieValue)
                    currentCount++;
            }
            if (currentCount >= 5) {
                found5K = true;
            }
        }
        for (int dieValue = 1; dieValue <=numOfSides; dieValue++) {
            currentCount = 0;
            for (int diePosition = 0; diePosition < numOfDice; diePosition++) {
                if (finalRoll.get(diePosition).getSideUp() == dieValue)
                    currentCount++;
            }
            if (currentCount >= 3) {
                found3K = true;
                value3K = dieValue;
            }
        }
        if(found3K) {
            for (int dieValue = 1; dieValue <= numOfSides; dieValue++) {
                currentCount = 0;
                for (int diePosition = 0; diePosition < numOfDice; diePosition++) {
                    if (finalRoll.get(diePosition).getSideUp() == dieValue)
                        currentCount++;
                }
                if (currentCount >= 2 && dieValue != value3K)
                    found2K = true;
            }
        }

        if ((found2K && found3K) || found5K)
            foundFH = true;

        return foundFH;
    }

}
