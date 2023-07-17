import java.awt.event.KeyEvent;

public class Mouth extends Sprite{
	int dx = 0;
	public Mouth(int x, int y) {
		super(x, y);
		
		initMouth();
	}

	private void initMouth() {
		loadImage("res/mouth.png", 100, 100);
		
	}
	
	
	public void move(int b_width) {
		x += dx;

		if (x > b_width - width - 15) {
			 x =  b_width - width - 15;
		}
		if (x < 0) {
			x = 0;
		}
	}
	
	public void keyPressed(KeyEvent e) {
		int key = e.getKeyCode();
		if (key == e.VK_RIGHT) {
			dx = 6;
		}
		if (key == e.VK_LEFT) {
			dx = -6;
		}
	}

}
