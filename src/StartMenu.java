import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.TimerTask;

import javax.imageio.ImageIO;
import javax.swing.*;


@SuppressWarnings("serial")
public class StartMenu extends JFrame{

	public JButton start100Button;
	public JButton endlessButton;
	public JButton exitButton;	
	private static boolean isRunning = false;
	public static Board run;
	public static BoardRun test;
	public JLabel label;
	private static int menuWidth = 600, menuHeight = 600;


	private Image image;
	menuChoices endless, oneHundred, exit;

	public StartMenu() {
		initMenu();
	}

	private void initMenu() {
		setTitle("Start Menu");
		setSize(menuWidth, menuHeight);
		setResizable(false);
		setVisible(true);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		bListener bListen = new bListener();
		start100Button = new JButton();
		oneHundred = new menuChoices("res/100modeBorder.png",150,25);
		start100Button.setIcon(new ImageIcon(oneHundred.image));
		start100Button.setActionCommand("100 Mode");
		start100Button.addActionListener(bListen);
		start100Button.setBounds(oneHundred.x, oneHundred.y, 300, 150);

		endlessButton = new JButton();
		endless = new menuChoices("res/endlessmodeBorder.png",150,350);
		endlessButton.setIcon(new ImageIcon(endless.image));
		endlessButton.setActionCommand("Endless Mode");
		endlessButton.addActionListener(bListen);
		endlessButton.setBounds(endless.x, endless.y, 300, 150);


		exitButton = new JButton("Exit Game");
		exitButton.addActionListener(bListen);
		exitButton.setBounds(150, 500, 300, 50);
		exitButton.setBackground(Color.red);
		//start100Button.setBackground(Color.black);
		//startButton.setForeground(Color.white);
		//exitButton.setForeground(Color.white);
		JPanel panel = new JPanel();
		panel.add(endlessButton);
		panel.add(start100Button);
		panel.add(exitButton);
		panel.setLayout(null);
		panel.setBackground(Color.black);
		this.add(panel);
	}

	public class menuChoices extends Sprite {
		public menuChoices(String imagePath, int x, int y) {
			super(x, y);
			image = returnImage(imagePath,300, 150);
		}

	}

	private class bListener implements ActionListener{
	    public void actionPerformed(ActionEvent event){
	    	if (event.getActionCommand() == "100 Mode") {
	    		setVisible(false);
	    		setRunning(true);
	    		run = new Board(100);
	    	}
			if (event.getActionCommand() == "Endless Mode") {
				setVisible(false);
				setRunning(true);
				run = new Board();
			}
	    	else if (event.getActionCommand() == "Exit Game") {
	    		System.exit(0);
	    	}
	    }
	}
	
	public static void setRunning(boolean running) {
		isRunning = running;
	}
	
	public static boolean isRunning() {
		return isRunning;
	}
	
}
