import java.awt.*;
import java.awt.event.*;

import javax.swing.*;


@SuppressWarnings("serial")
public class EndScreen extends JFrame{
    private JButton restartButton;
    private int burgsCaught;
    private JLabel stats;
    private JButton exitButton;
    private JFrame frame;

    public EndScreen(JFrame frame, int burgsCaught) {
        this.burgsCaught = burgsCaught;
        this.frame = frame;
        initMenu();
    }

    private void initMenu() {
        setTitle("Game Over");
        setSize(600, 600);
        setResizable(false);
        setVisible(true);
        this.setLocationRelativeTo(null);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        restartButton = new JButton("Restart Game");
        bListener bListen = new bListener();
        restartButton.addActionListener(bListen);
        restartButton.setBounds(150, 50, 300, 190);

        exitButton = new JButton("Exit Game");
        exitButton.addActionListener(bListen);
        exitButton.setBounds(150, 280, 300, 190);
        exitButton.setBackground(Color.red);
        restartButton.setBackground(Color.green);
        restartButton.setForeground(Color.white);
        exitButton.setForeground(Color.white);



        JPanel panel = new JPanel();
        stats = new JLabel("Congratulations, you caught " + burgsCaught + " burgers.");
        panel.add(stats);
        panel.add(exitButton);
        panel.add(restartButton);
        panel.add(exitButton);
        //panel.add(stats);
        panel.setLayout(null);
        panel.setBackground(Color.black);
        this.add(panel);
    }

    private class bListener implements ActionListener{
        public void actionPerformed(ActionEvent event){
           if (event.getActionCommand() == "Exit Game") {
                System.exit(0);
            }
        }
    }

}