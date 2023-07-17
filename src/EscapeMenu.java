import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

@SuppressWarnings("serial")
public class EscapeMenu extends JFrame{

	public JButton resumeButton;	
	public JButton returnButton;	

	public EscapeMenu() {
		initMenu();
	}

	private void initMenu() {
		setTitle("Options");
		setSize(600, 600);
		setResizable(false);
		setVisible(true);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		JButton startButton = new JButton("Resume Game");
		bListener bListen = new bListener();
		startButton.addActionListener(bListen);
		startButton.setBounds(150, 50, 300, 200);
		JButton exitButton = new JButton("Return to Menu");
		exitButton.addActionListener(bListen);

		exitButton.setBounds(150, 300, 300, 200);
		exitButton.setBackground(Color.red);
		startButton.setBackground(Color.green);
		startButton.setForeground(Color.white);
		exitButton.setForeground(Color.white);
		JPanel panel = new JPanel();
		panel.add(startButton);
		panel.add(exitButton);
		panel.setLayout(null);
		panel.setBackground(Color.black);
		this.add(panel);
	}

	private class bListener implements ActionListener{
	    public void actionPerformed(ActionEvent event){
	    	if (event.getActionCommand() == "Resume Game") {
	    		setVisible(false);
	    		BoardRun test = new BoardRun();
	    		test.setVisible(true);
	    	}
	    	else if (event.getActionCommand() == "Exit Game") {
	    		System.exit(0);
	    	}
	    }
	}
}