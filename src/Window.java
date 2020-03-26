import javax.sound.sampled.Line;
import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.*;
import java.util.ArrayList;
import java.util.Scanner;

public class Window extends JFrame{
    private Game game;
    private JPanel mainPanel;
    private JPanel leftPanel;
    private JPanel bottomPanel;
    private JPanel rightPanel;
    private JLabel displayDiceLabel;
    private JLabel displaySideLabel;
    private JLabel displayRerollLabel;
    private JLabel diceLabel;
    private JLabel sidesLabel;
    private JButton configBtn;
    private JButton rollButton;
    private JButton scorecardButton;
    private JPanel middlePanel = new JPanel();
    private JButton startButton;
    private JLabel yahtzeeName;
    private JDialog dialog;
    private JDialog scorecard;
    private Window window;
    private JButton end = new JButton("enter");
    private String[] diceOptions = {"5","6","7"};
    private String[] sideOptions = {"6","8","12"};
    private JComboBox dice = new JComboBox(diceOptions);
    private JComboBox sides = new JComboBox(sideOptions);
    private boolean playing = false;
    private int rollsLeft = 3;
    private int numOfDice;
    private JButton ok = new JButton("ok");

    ImageIcon diceImage;
    Image img;
    JLabel die1 = new JLabel();
    JLabel die2 = new JLabel();
    JLabel die3 = new JLabel();
    JLabel die4 = new JLabel();
    JLabel die5 = new JLabel();
    JLabel die6 = new JLabel();
    JLabel die7 = new JLabel();

    public Window(String title, Game game){
        super(title);
        this.game = game;
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(mainPanel);
        this.setBackground(Color.LIGHT_GRAY);
        this.pack();
        this.setVisible(true);
        window = this;
        add(middlePanel);
        middlePanel.setVisible(true);

        configBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if(!playing) {
                    dialog = new JDialog(window, "Configuration", Dialog.ModalityType.APPLICATION_MODAL);
                    dialog.setTitle("Configuration");
                    dialog.setLocation(400, 300);
                    dialog.setSize(300, 300);
                    dialog.getContentPane().add(new JLabel("Number of Dice:"));
                    dialog.getContentPane().add(dice);
                    dialog.getContentPane().add(new JLabel("Number of sides:"));
                    dialog.getContentPane().add(sides);
                    dialog.getContentPane().add(end);
                    dialog.setLayout(new GridLayout(3, 2));
                    dialog.setVisible(true);
                }
            }
        });
        end.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                setDisplayDiceLabel(Integer.parseInt(dice.getSelectedItem().toString()));
                setDisplaySideLabel(Integer.parseInt(sides.getSelectedItem().toString()));
                try{
                    PrintStream outFile = new PrintStream("yahtzeeConfig.txt");

                    outFile.println(Integer.parseInt(sides.getSelectedItem().toString()));
                    outFile.println(Integer.parseInt(dice.getSelectedItem().toString()));
                }
                catch (FileNotFoundException exception){
                    System.out.println("file not found");
                    System.exit(1);
                }
                dialog.dispose();
            }
        });

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                createHand();
            }
        });

        scorecardButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                game.printScorecard();
                String line = "";

                scorecard = new JDialog(window, "Scorecard", Dialog.ModalityType.APPLICATION_MODAL);
                scorecard.setTitle("Scorecard");
                scorecard.setLocation(400, 300);
                scorecard.setSize(300, 500);
                scorecard.setLayout(new GridBagLayout());
                GridBagConstraints c = new GridBagConstraints();

                int i = 0;


                Scanner scan = new Scanner(System.in);
                Scanner inFile = null;
                File text = new File("printScorecard.txt");
                try {
                    inFile = new Scanner(text);
                    while (inFile.hasNextLine()) {
                        line = inFile.nextLine();
                        c.fill = GridBagConstraints.HORIZONTAL;
                        c.gridx =0;
                        c.gridy=i;
                        scorecard.getContentPane().add(new JLabel(line),c);
                        i++;
                    }
                } catch (FileNotFoundException ex) {
                    System.out.println("file not found");
                    System.exit(1);
                }
                c.fill = GridBagConstraints.HORIZONTAL;
                c.gridx =0;
                c.gridy=i;
                scorecard.getContentPane().add(ok,c);
                scorecard.setVisible(true);
            }
        });
        ok.addActionListener(new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                scorecard.dispose();
            }
        });
        rollButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                rollHand();
            }
        });
    }

    public void setDisplayDiceLabel(int num) {
        this.displayDiceLabel.setText(Integer.toString(num));
    }
    public String getDisplayDiceLabel(){
        return this.displayDiceLabel.getText();
    }
    public void setDisplaySideLabel(int num){
        this.displaySideLabel.setText(Integer.toString(num));
    }

    public void createHand(){
        if(!playing) {
            playing = true;
            System.out.println("creating a hand");
            game.playGame();
        }
    }

    public void displayHand(ArrayList<Dice> hand){
        int numOfDice = Integer.parseInt(getDisplayDiceLabel());
        if(numOfDice >= 5){
            die1.setIcon(new ImageIcon(new ImageIcon("Images/"+hand.get(0).getSideUp()+".png").getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));
            die2.setIcon(new ImageIcon(new ImageIcon("Images/"+hand.get(1).getSideUp()+".png").getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));
            die3.setIcon(new ImageIcon(new ImageIcon("Images/"+hand.get(2).getSideUp()+".png").getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));
            die4.setIcon(new ImageIcon(new ImageIcon("Images/"+hand.get(3).getSideUp()+".png").getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));
            die5.setIcon(new ImageIcon(new ImageIcon("Images/"+hand.get(4).getSideUp()+".png").getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));

            middlePanel.add(die1);
            middlePanel.add(die2);
            middlePanel.add(die3);
            middlePanel.add(die4);
            middlePanel.add(die5);
        }
        if(numOfDice >= 6){
            die6.setIcon(new ImageIcon(new ImageIcon("Images/"+hand.get(5).getSideUp()+".png").getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));

            middlePanel.add(die6);
        }
        if(numOfDice == 7){
            die7.setIcon(new ImageIcon(new ImageIcon("Images/"+hand.get(6).getSideUp()+".png").getImage().getScaledInstance(100,100,Image.SCALE_DEFAULT)));

            middlePanel.add(die7);
        }
        this.setSize(new Dimension(700, 300));

        checkKept();
    }

ArrayList<Dice> newRoll;

    public void rollHand(){
        newRoll = game.rollHand();
        if(rollsLeft == 0 ) {
            for (int i = 1; i < numOfDice + 1; i++) {
                game.setKeptDice(i, false);
            }
            checkKept();
            displayHand(newRoll);

            possibleScores();
        } else if(rollsLeft>0){
            displayHand(newRoll);
        }
        rollsLeft--;
    }

    public void possibleScores(){
        ArrayList<String[]> unusedScoreList = new ArrayList<String[]>();
        JLabel label;
        displayHand(game.sortHand());
        unusedScoreList = game.getPossibleScores();

        rightPanel = new JPanel(new GridBagLayout());
        System.out.println("displaying possible scores");
        GridBagConstraints c = new GridBagConstraints();
        for(int i = 0; i<unusedScoreList.size(); i++) {
            label = new JLabel(unusedScoreList.get(i)[0]+":  ");
            c.fill = GridBagConstraints.HORIZONTAL;
            c.gridx = 0;
            c.gridy = i;
            rightPanel.add(label,c);
            label = new JLabel(unusedScoreList.get(i)[3]);
            c.gridx = 2;
            c.gridy = i;
            rightPanel.add(label,c);
            JButton btn = new JButton(unusedScoreList.get(i)[0]);
            c.gridx = 3;
            c.gridy = i;
            rightPanel.add(btn,c);


            btn.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    game.chooseScore(btn.getText());
                    System.out.println(btn.getText());
                    reset();
                }
            });
        }

        this.add(rightPanel,BorderLayout.EAST);
        rightPanel.setVisible(true);
        pack();

        game.scoreHand();
    }

    public void finishGame(){
        middlePanel.removeAll();
        middlePanel.add(new JLabel("You finished the game!"));
    }

    public void reset(){
        playing = false;
        rollsLeft = 3;
        game.resetReRoll();
        rightPanel.setVisible(false);
        createHand();
    }

    public void checkKept(){
        numOfDice = Integer.parseInt(getDisplayDiceLabel());
        die1.addMouseListener(new MouseListener() {
            boolean clicked = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!clicked) {
                    die1.setBorder(BorderFactory.createLineBorder(Color.RED));
                    clicked = true;
                    game.setKeptDice(1,true);
                } else {
                    die1.setBorder((null));
                    clicked = false;
                    game.setKeptDice(1,false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        die2.addMouseListener(new MouseListener() {
            boolean clicked = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!clicked) {
                    die2.setBorder(BorderFactory.createLineBorder(Color.RED));
                    clicked = true;
                    game.setKeptDice(2,true);
                } else {
                    die2.setBorder((null));
                    clicked = false;
                    game.setKeptDice(2,false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        die3.addMouseListener(new MouseListener() {
            boolean clicked = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!clicked) {
                    die3.setBorder(BorderFactory.createLineBorder(Color.RED));
                    clicked = true;
                    game.setKeptDice(3,true);
                } else {
                    die3.setBorder((null));
                    clicked = false;
                    game.setKeptDice(3,false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        die4.addMouseListener(new MouseListener() {
            boolean clicked = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!clicked) {
                    die4.setBorder(BorderFactory.createLineBorder(Color.RED));
                    clicked = true;
                    game.setKeptDice(4,true);
                } else {
                    die4.setBorder((null));
                    clicked = false;
                    game.setKeptDice(4,false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        die5.addMouseListener(new MouseListener() {
            boolean clicked = false;
            @Override
            public void mouseClicked(MouseEvent e) {
                if(!clicked) {
                    die5.setBorder(BorderFactory.createLineBorder(Color.RED));
                    clicked = true;
                    game.setKeptDice(5,true);
                } else {
                    die5.setBorder((null));
                    clicked = false;
                    game.setKeptDice(5,false);
                }
            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {

            }

            @Override
            public void mouseExited(MouseEvent e) {

            }
        });
        if(numOfDice>=6) {
            die6.addMouseListener(new MouseListener() {
                boolean clicked = false;

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!clicked) {
                        die6.setBorder(BorderFactory.createLineBorder(Color.RED));
                        clicked = true;
                        game.setKeptDice(6, true);
                    } else {
                        die6.setBorder((null));
                        clicked = false;
                        game.setKeptDice(6, false);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
        if(numOfDice==7) {
            die7.addMouseListener(new MouseListener() {
                boolean clicked = false;

                @Override
                public void mouseClicked(MouseEvent e) {
                    if (!clicked) {
                        die7.setBorder(BorderFactory.createLineBorder(Color.RED));
                        clicked = true;
                        game.setKeptDice(7, true);
                    } else {
                        die7.setBorder((null));
                        clicked = false;
                        game.setKeptDice(7, false);
                    }
                }

                @Override
                public void mousePressed(MouseEvent e) {

                }

                @Override
                public void mouseReleased(MouseEvent e) {

                }

                @Override
                public void mouseEntered(MouseEvent e) {

                }

                @Override
                public void mouseExited(MouseEvent e) {

                }
            });
        }
    }

    public void decrementRollsLeft(){
        rollsLeft--;
    }
}
