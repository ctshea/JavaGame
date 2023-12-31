import java.awt.*;
import java.awt.event.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class EscapeMenu extends JFrame{
	private boolean bgMusicToggle=true, eatSoundToggle=true, missSoundToggle=true;	//true for on false for off
	private JCheckBox bgMusic, eatSound, missSound;
	private JPanel bgMusicWrap, eatSoundWrap, missSoundWrap;	//wrappers to help the GUI for checkboxes
	private JButton resumeButton;
	private JButton exitButton;
	private JFrame frame;

	public EscapeMenu(JFrame frame) {
		this.frame = frame;
		initMenu();
	}

	private void initMenu() {
		setTitle("Options");
		setSize(600, 600);
		setResizable(false);
		setVisible(true);
		this.setLocationRelativeTo(null);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		resumeButton = new JButton("Resume Game");
		bListener bListen = new bListener();
		iListener iListen = new iListener();
		resumeButton.addActionListener(bListen);
		resumeButton.setBounds(150, 50, 300, 190);

		exitButton = new JButton("Exit Game");
		exitButton.addActionListener(bListen);
		exitButton.setBounds(150, 280, 300, 190);
		exitButton.setBackground(Color.red);
		resumeButton.setBackground(Color.green);
		resumeButton.setForeground(Color.white);
		exitButton.setForeground(Color.white);

		bgMusic = new JCheckBox();
		bgMusic.setName("BgMusic");
		bgMusic.addItemListener(iListen);
		bgMusicWrap = new JPanel();
		bgMusicWrap.add(new JLabel("Disable Music"));
		bgMusicWrap.add(bgMusic);
		bgMusicWrap.setBounds(30, 500, 160, 30);

		eatSound = new JCheckBox();
		eatSound.setName("eatSound");
		eatSound.addItemListener(iListen);
		eatSoundWrap = new JPanel();
		eatSoundWrap.add(new JLabel("Disable Eat Sound"));
		eatSoundWrap.add(eatSound);
		eatSoundWrap.setBounds(220, 500, 160, 30);

		missSound = new JCheckBox();
		missSound.setName("missSound");
		missSound.addItemListener(iListen);
		missSoundWrap = new JPanel();
		missSoundWrap.add(new JLabel("Disable Miss Sound"));
		missSoundWrap.add(missSound);
		missSoundWrap.setBounds(410, 500, 160, 30);


		JPanel panel = new JPanel();
		panel.add(resumeButton);
		panel.add(exitButton);
		panel.add(bgMusicWrap);
		panel.add(eatSoundWrap);
		panel.add(missSoundWrap);
		panel.setLayout(null);
		panel.setBackground(Color.black);
		this.add(panel);
	}

	private class bListener implements ActionListener{
	    public void actionPerformed(ActionEvent event){
	    	if (event.getActionCommand() == "Resume Game") {
	    		setVisible(false);
				resumeGame(frame);
	    	}
	    	else if (event.getActionCommand() == "Exit Game") {
	    		System.exit(0);
	    	}
	    }
	}

	private class iListener implements ItemListener{
		@Override
		public void itemStateChanged(ItemEvent e) {
			JCheckBox temp = (JCheckBox)e.getSource();
			String checkboxName = temp.getName();
			if (e.getStateChange() == ItemEvent.SELECTED) {
				if (checkboxName.equals("BgMusic")) {
					bgMusicToggle = false;
				} else if(checkboxName.equals("missSound")) {
					missSoundToggle = false;
				} else {
					eatSoundToggle = false;
				}
			} else {
				if (checkboxName.equals("BgMusic")) {
					bgMusicToggle = true;
				} else if(checkboxName.equals("missSound")) {
					missSoundToggle = true;
				} else {
					eatSoundToggle = true;
				}
			}
			Board.updateSettings();
		}
	}

	public void resumeGame(JFrame frame) {
		this.setVisible(false);
		frame.setVisible(true);
		StartMenu.setRunning(true);
	}

	public boolean isBgMusicToggle() {return bgMusicToggle;}
	public boolean isEatSoundToggle() {return eatSoundToggle;}
	public boolean isMissSoundToggle() {return missSoundToggle;}

	public void setBgMusic(boolean set) {
		bgMusicToggle = set;
	}
}