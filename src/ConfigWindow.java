import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Scanner;

public class ConfigWindow extends JFrame{
    private JPanel configPanel;
    private JTextField diceText;
    private JLabel dieLabel;
    private JTextField sideText;
    private JTextField rerollText;
    private JLabel sideLabel;
    private JButton enterButton;
    private JLabel rerollLabel;
    private JButton skipButton;

    private int number_of_sides;
    private int number_of_dice;
    private int rerollInput;

    public ConfigWindow(String title){
        super(title);

        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setContentPane(configPanel);
        this.setBackground(Color.LIGHT_GRAY);
        this.setSize(800,500);
        this.setLocation(500,300);
        this.pack();
        this.setVisible(true);
        skipButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });
        enterButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                number_of_sides = Integer.parseInt(sideText.getText());
                number_of_dice = Integer.parseInt(diceText.getText());
                rerollInput = Integer.parseInt(rerollText.getText());
                try{
                    PrintStream outFile = new PrintStream("yahtzeeConfig.txt");

                    outFile.println(number_of_sides);
                    outFile.println(number_of_dice);
                    outFile.println(rerollInput+1);
                }
                catch (FileNotFoundException exception){
                    System.out.println("file not found");
                    System.exit(1);
                }
                dispose();
            }
        });
    }
}
