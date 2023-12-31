import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.Timer;
import java.util.TimerTask;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

@SuppressWarnings("serial")
public class Board extends JPanel {

	public final int B_WIDTH = 600;
	public final int B_HEIGHT = 600;
	private int burgDelay = 1000;
	private int burgInterval = 1000;
	private final int DELAY = 100;
	private final int INTERVAL = 30;
	private int entry = 0;
	private int random;
	private boolean collideChecked = false;
	private long collideTimer;
	private long backgroundResetTimer = 0;
	private boolean firstCollision = false;
	private int count = 0;
	private int endlessCounter = 0;
	private int counter = 0;
	private int numBurgers;
	private int burgsCaught = 0;
	public static int burgsMissed = 0;
	private int burgsRemaining = 100;
	private int countI = 0;
	private boolean isPaused = false;
	private boolean endless = false;
	private static Clip clip;
	
	public static EscapeMenu options;
	public static EndScreen endScreen;

	public static JFrame frame;
	private JLabel label;
	Timer burgTimer;
	private Timer timer;
	private Mouth mouth;
	private ClosedMouth cMouth;
	Random rand = new Random();
	private int[] burgerCoords;
	private Burgers burger;
	private List<Burgers> burgers;


	public Board(int numBurgers) {
		this.numBurgers = numBurgers;
		burgerCoords = new int[numBurgers];
		initBoard();
	}

	public Board() {
		this.numBurgers = 10;	//arbitrary number just to have some to remove and add more
		burgerCoords = new int[numBurgers];
		endless = true;
		initBoard();
	}

	//public Board() {
		//BoardRun test = new BoardRun();
		//test.setVisible(true);
		
	//}

	private void initBoard() {
		label = new JLabel("Burgers remaining: " + numBurgers +" || Burgers missed: " + burgsMissed);
		label.setHorizontalAlignment(SwingConstants.CENTER);
		label.setBackground(Color.black);
		label.setForeground(Color.white);
		frame = new JFrame();
		frame.setLayout(new BorderLayout());
		frame.setResizable(false);
		frame.pack();		
		frame.setTitle("Game");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setLocationRelativeTo(null);
		frame.setVisible(true);
		frame.setSize(new Dimension(B_WIDTH, B_HEIGHT + 40));
		frame.setLocationRelativeTo(null);
		
		this.setLayout(new BorderLayout());
		this.addKeyListener(new KAdapter());
		this.setFocusable(true);
		this.setBackground(Color.black);
		this.setPreferredSize(new Dimension(B_WIDTH, B_HEIGHT));
		this.setDoubleBuffered(true);
		
		this.add(label, BorderLayout.SOUTH);
		frame.add(this);
		
		options = new EscapeMenu(frame);
		options.setVisible(false);


		mouth = new Mouth(0, 0);
		burger = new Burgers(0, 0);
		cMouth = new ClosedMouth(0, 0);
		startBGMusic();
		initBurgers();

		timer = new Timer();
		timer.scheduleAtFixedRate(new ScheduleTask(), DELAY, INTERVAL);
		burgTimer = new Timer();
		burgTimer.scheduleAtFixedRate(new burgTask(), burgDelay, burgInterval);
	}

	private void startBGMusic() {
		try {
			AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("res/bgmusic.wav"));
			clip = AudioSystem.getClip();
			clip.open(audioIn);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();

		} catch (UnsupportedAudioFileException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (LineUnavailableException e) {
			e.printStackTrace();
		}
	}
	private void initBurgers() {
		burgers = new ArrayList<>();
		for (int i = 0; i <= numBurgers - 1; i++) {
			random = rand.nextInt(B_WIDTH - burger.getWidth() - 10);
			burgerCoords[i] = random;
			burgers.add(new Burgers(burgerCoords[i], 0));
		}
	}


	public void updateBurgers() {
		//counter just to count number of burgers dropped. countI to count number of burgers either caught or missed.
		//count is incremented every one second
		if (!endless) {
			if (counter == count && counter < numBurgers) {        //send another burger from the top once timer increments
				burgers.get(counter - countI).setStarted(true);
				burgsRemaining--;
				counter++;
			}
		} else {    //if playing endless mode
			int burgerToMove = 0;
			for (int i = 0; i < burgers.size(); i++) {    //to set the first non STARTED burger to move down screen
				if (!burgers.get(i).isStarted) {
					burgerToMove = i;
					break;
				}
			}
			if (counter == count) {	//every second
				burgers.get(burgerToMove).setStarted(true);
				counter++;
			}
		}

		for (int i = 0; i < burgers.size(); i++) {
			if (burgers.get(i).isStarted && burgers.get(i).isVisible()) {
				burgers.get(i).move(B_HEIGHT);
			} else {
				if (burgers.get(i).isVisible() == false) {
					burgers.remove(i);
					random = rand.nextInt(B_WIDTH - burger.getWidth() - 10);
					burgers.add(new Burgers(random, 0));
					if (burgsMissed >= 3 && endless) {
						StartMenu.setRunning(false);
						frame.setVisible(false);
						options.setBgMusic(false);
						endScreen = new EndScreen(frame, burgsCaught);
						endScreen.setVisible(true);
						} else if (!endless && burgsRemaining == 0){
						StartMenu.setRunning(false);
						frame.setVisible(false);
						options.setBgMusic(false);
						endScreen = new EndScreen(frame, burgsCaught);
						endScreen.setVisible(true);
					}
				}
			}
		}
	}

	private void updateMouth() {
		mouth.move(B_WIDTH);

	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		drawImage(g);
	}

	private void drawImage(Graphics g) {

		if (entry <= 1) {
			mouth.setX((B_WIDTH / 2) - (mouth.getWidth() / 2));
			mouth.setY(B_HEIGHT - mouth.getHeight());
			g.drawImage(mouth.getImage(), mouth.getX(), mouth.getY(), this);
			entry++;
		} else {
			if (firstCollision == true && System.currentTimeMillis() < collideTimer + 200) {
				g.drawImage(cMouth.getImage(), mouth.getX(), mouth.getY(), this);
				collideChecked = true;
			} else {
				g.drawImage(mouth.getImage(), mouth.getX(), mouth.getY(), this);
				collideChecked = false;
			}

			for (int i = 0; i < burgers.size(); i++) {
				if (burgers.get(i).isStarted && burgers.get(i).isVisible()) {
					g.drawImage(burgers.get(i).getImage(), burgers.get(i).getX(), burgers.get(i).getY(), this);
				}
			}

			if (!endless) {
				label.setText("Burgers remaining: " + burgsRemaining + " || Burgers missed: " + burgsMissed+ " || Burgers caught: " + burgsCaught);
			} else {
				label.setText("Burgers missed: " + burgsMissed);
			}


			Toolkit.getDefaultToolkit().sync();
		}
	}

	public static void updateSettings() {
		if (!options.isBgMusicToggle()) {
			clip.stop();
		} else {
			clip.start();
		}

	}

	public void setBackgroundColor() {
		if (backgroundResetTimer != 0) {	//atleast one burger has been missed
			if (System.currentTimeMillis() < backgroundResetTimer + 250) {
					this.setBackground(Color.red);
				}
			else {
				this.setBackground(Color.black);
			}
		}
	}

	public void collisionCheck() {
		Rectangle r1 = mouth.getBounds();
		for (Burgers burg : burgers) {
			Rectangle r2 = burg.getBounds();
			if (r1.intersects(r2)) {
				burgsCaught++;
				if (options.isEatSoundToggle()) {
				try {
					AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("res/wow.wav"));
					Clip clip = AudioSystem.getClip();
					clip.open(audioIn);
					clip.start();
				} catch (UnsupportedAudioFileException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} catch (LineUnavailableException e) {
					e.printStackTrace();
				}
			}
				label.setText(String.valueOf(countI + 1));
				countI++;
				burg.setVisible(false);
				firstCollision = true;

				if (collideChecked == false) {
					collideTimer = System.currentTimeMillis();
				}

			}
			if (burg.getY() == B_HEIGHT) {
				countI++;
				burg.setVisible(false);
				burgsMissed += 1;
				backgroundResetTimer = System.currentTimeMillis();
				if (options.isMissSoundToggle()) {
					try {
						AudioInputStream audioIn = AudioSystem.getAudioInputStream(new File("res/miss.wav"));
						Clip clip = AudioSystem.getClip();
						clip.open(audioIn);
						clip.start();

					} catch (UnsupportedAudioFileException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					} catch (LineUnavailableException e) {
						e.printStackTrace();
					}
				}
			}

		}
	}

	public class KAdapter extends KeyAdapter {
		@SuppressWarnings("static-access")
		@Override
		public void keyPressed(KeyEvent e) {
			int key = e.getKeyCode();
			if (key == e.VK_ESCAPE) {
				StartMenu.setRunning(false);
				frame.setVisible(false);
				//isPaused = true;
				options.setVisible(true);
			}
			mouth.keyPressed(e);
		}
	}

	public class burgTask extends TimerTask {

		@Override
		public void run() {
			if (StartMenu.isRunning()) {
				if (!endless) {
					if (count < numBurgers) {
						count++;
					}
				} else {
					count++;
				}

			}
		}

	}

	public class ScheduleTask extends TimerTask {
		@Override
		public void run() {
			if (StartMenu.isRunning()) {
				updateBurgers();
				updateMouth();
				collisionCheck();
				repaint();
				setBackgroundColor();
			}
		}

	}
}
