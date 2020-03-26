import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * This program creates and manages the scorecard
 * CPSC 224
 * HW 4
 * No sources to cite
 *
 * @author Sami Blevens
 * @version 2/24/20 v4
 */
public class Scorecard {
    private int sides;
    private Calculator calc;
    private Scanner scorecard_inFile;
    private PrintStream scorecard_outFile;
    private ArrayList<String[]> scorecardList = new ArrayList<String[]>();
    private ArrayList<String[]> unusedScoresList = new ArrayList<>();

    Scorecard(int sides, Calculator calc){
        //new scorecard
        this.sides = sides;
        this.calc = calc;
        try{
            scorecard_inFile = new Scanner(new File("scorecard.txt"));
            scorecard_outFile = new PrintStream("scorecard.txt");
        }
        catch(FileNotFoundException e){
            System.out.println("scorecard file not found");
            System.exit(1);
        }

        createScorecard();
        readScorecard();
    }

    /**
     * creates a blank scorecard.txt file
     */
    public void createScorecard(){
        //upper
        for(int i=0; i<sides; i++){
            scorecard_outFile.print(i+1);
            scorecard_outFile.print(",n,u,0 \n");
        }
        //lower
        scorecard_outFile.print("3K,n,l,0 \n");
        scorecard_outFile.print("4K,n,l,0 \n");
        scorecard_outFile.print("FH,n,l,0 \n");
        scorecard_outFile.print("SS,n,l,0 \n");
        scorecard_outFile.print("LS,n,l,0 \n");
        scorecard_outFile.print("Y,n,l,0 \n");
        scorecard_outFile.print("C,n,l,0 \n");
        //close file

    }

    /**
     * reads the scorecard.txt file into an ArrayList of all current scores and an
     * ArrayList of unused scorecard places
     */
    public void readScorecard(){
        //String[] lineList = new String[4];
        scorecardList.clear();
        unusedScoresList.clear();
        try{
            scorecard_inFile = new Scanner(new File("scorecard.txt"));
        }
        catch(FileNotFoundException e){
            System.out.println("scorecard file not found");
            System.exit(1);
        }
        do{
            int index = 0;
            String line = scorecard_inFile.nextLine();
            //Name code
            String name = "";
            while(line.charAt(index)!=','){
                name = name + line.charAt(index);
                index++;
            }
            //System.out.println(name);
            index++;
            //used or not
            String used = "";
            while(line.charAt(index)!=','){
                used = used + line.charAt(index);
                index++;
            }
            //System.out.println(used);
            index++;
            //Section upper or lower
            String section = "";
            while(line.charAt(index)!=','){
                section = section + line.charAt(index);
                index++;
            }
            //System.out.println(section);
            index++;
            //score input
            String score = "";
            while(line.charAt(index)!=' '){
                score = score + line.charAt(index);
                index++;
            }
            //System.out.println(score);

            scorecardList.add(new String[]{name, used, section, score});
            if(used.equals("n")){
                unusedScoresList.add(new String[]{name, used, section, score});
            }
        }while(scorecard_inFile.hasNextLine());
        scorecard_inFile.close();
    }

    /**
     * Prints the possible scores for the player to choose from based on the
     * unused scorecard lines ArrayList
     */
    public ArrayList<String[]> printPossibleScores() {
        //line 316 in python yahtzee
        //check file for used or not,
        readScorecard();
        int num;
        int count;
        //if not used, display the score for that line
        for (int i = 0; i < unusedScoresList.size(); i++) {
            //if is in the upper section...
            if (unusedScoresList.get(i)[2].equals("u")) {
                //set score equal to the name * the total of the number
                num = Integer.parseInt(unusedScoresList.get(i)[0]);
                count = calc.count(unusedScoresList.get(i)[0]);
                unusedScoresList.get(i)[3] = Integer.toString(num * count);
            } else { //lower section
                if (unusedScoresList.get(i)[0].equals("3K")) {
                    if (calc.maxOfAKindFound() >= 3) {
                        unusedScoresList.get(i)[3] = Integer.toString(calc.totalAllDice());
                    }
                }
                if (unusedScoresList.get(i)[0].equals("4K")) {
                    if (calc.maxOfAKindFound() >= 4) {
                        unusedScoresList.get(i)[3] = Integer.toString(calc.totalAllDice());
                    }
                }
                if (unusedScoresList.get(i)[0].equals("FH")) {
                    if (calc.fullHouseFound()) {
                        unusedScoresList.get(i)[3] = "25";
                    }
                }
                if (unusedScoresList.get(i)[0].equals("SS")) {
                    if (calc.maxStraightFound() >= 4) {
                        unusedScoresList.get(i)[3] = "30";
                    }
                }
                if (unusedScoresList.get(i)[0].equals("LS")) {
                    if (calc.maxStraightFound() >= 5) {
                        unusedScoresList.get(i)[3] = "40";
                    }
                }
                if (unusedScoresList.get(i)[0].equals("Y")) {
                    if (calc.maxOfAKindFound() >= 5) {
                        unusedScoresList.get(i)[3] = "50";
                    }
                }
                if (unusedScoresList.get(i)[0].equals("C")) {
                    unusedScoresList.get(i)[3] = Integer.toString(calc.totalAllDice());
                }
            }
        }
        System.out.println("Scoring Options: ");
        for (int i = 0; i < unusedScoresList.size(); i++) {
            System.out.println("score is " + unusedScoresList.get(i)[3] +
                    " if you choose the " + unusedScoresList.get(i)[0] + " line");
        }

        return unusedScoresList;
    }

    /**
     * takes an input and changes the scorecard.txt file to correspond with the value
     * chosen and places the score, and sets the used value to yes.
     */
    public void chooseScore(String choice){
        String scoreOfChoice;
        scoreOfChoice = "";
        System.out.println(choice);


        for(int i=0; i<unusedScoresList.size();i++){
            if(unusedScoresList.get(i)[0].equals(choice)){
                scoreOfChoice = unusedScoresList.get(i)[3];
            }
        }
        for(int i=0; i<scorecardList.size();i++){
            if(scorecardList.get(i)[0].equals(choice)){
                scorecardList.get(i)[3] = scoreOfChoice;
                scorecardList.get(i)[1] = "y";
            }
        }
        writeScorecard();
        System.out.println("wrote scorecard");
    }

    /**
     * reads the scorecard ArrayList and writes it to the scorecard.txt file
     */
    public void writeScorecard(){
        try{
            scorecard_outFile = new PrintStream("scorecard.txt");
        }
        catch(FileNotFoundException e){
            System.out.println("scorecard file not found");
            System.exit(1);
        }
        for(int i=0; i<scorecardList.size(); i++){
            scorecard_outFile.println(scorecardList.get(i)[0]+","+scorecardList.get(i)[1]+
            ","+ scorecardList.get(i)[2]+","+scorecardList.get(i)[3]+" ");
        }
    }


    /**
     * prints the scorecard board to the terminal
     */
    public void printScorecard(){
        try{
            scorecard_outFile = new PrintStream("printScorecard.txt");
        }
        catch(FileNotFoundException e){
            System.out.println("scorecard file not found");
            System.exit(1);
        }
        int upperTotal = 0;
        int bonus = 0;
        int lowerTotal = 0;
        int grandTotal;
        String lineTotal = "";

        scorecard_outFile.println("Line                  Score");
        scorecard_outFile.println("-----------------------------");
        for(int i=0;i<scorecardList.size();i++){
            if(scorecardList.get(i)[2].equals("u")){
                upperTotal += Integer.parseInt(scorecardList.get(i)[3]);
                lineTotal = scorecardList.get(i)[3];
                scorecard_outFile.println(scorecardList.get(i)[0]+"                                 "+lineTotal);
            }else if(scorecardList.get(i)[0].equals("3K")){
                if(upperTotal>=63){
                    bonus = 35;
                }
                scorecard_outFile.println("-----------------------------");
                scorecard_outFile.println("Sub Total                 "+Integer.toString(upperTotal));
                scorecard_outFile.println("Bonus                       "+Integer.toString(bonus));
                scorecard_outFile.println("-----------------------------");
                scorecard_outFile.println("Upper Total              "+Integer.toString(upperTotal+bonus)+"\n");
                lineTotal = scorecardList.get(i)[3];
                lowerTotal += Integer.parseInt(scorecardList.get(i)[3]);
                scorecard_outFile.println(scorecardList.get(i)[0]+"                              "+lineTotal);
            } else{
                lineTotal = scorecardList.get(i)[3];
                lowerTotal += Integer.parseInt(scorecardList.get(i)[3]);
                scorecard_outFile.println(scorecardList.get(i)[0]+"                               "+lineTotal);
            }
        }
        grandTotal = upperTotal+bonus+lowerTotal;
        scorecard_outFile.println("-----------------------------");
        scorecard_outFile.println("Lower Total             "+Integer.toString(lowerTotal));
        scorecard_outFile.println("-----------------------------");
        scorecard_outFile.println("Grand Total             "+Integer.toString(grandTotal)+"\n");
        scorecard_outFile.println();
    }

    /**
     * returns the ArrayList of the unused possible score lines
     * @return unusedScoresList() : the ArrayList of the unused possible score lines
     */
    public ArrayList<String[]> getUnusedScoresList(){
        return unusedScoresList;
    }
}
