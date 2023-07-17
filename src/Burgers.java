import java.awt.Color;

public class Burgers extends Sprite{
	
	protected boolean isStarted = false;

	
	public Burgers(int x, int y) {
		super(x, y);
		
		initBurgers();
	}

	private void initBurgers() {
		loadImage("res/burger.png", 35, 35);
		
	}
	
	public void move(int b_height) {
		y += 3;
		
		
	}
	
	public void setStarted(boolean started) {
		isStarted = started;
	}
	
	public boolean isStarted() {
		return isStarted;
	}

}
