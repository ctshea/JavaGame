import java.awt.Image;
import java.awt.Rectangle;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;

public abstract class Sprite {

	protected int x;
	protected int y;
	protected boolean visible;
	protected int width;
	protected int height;
	protected Image image;
	
	
	public Sprite(int x, int y) {
		this.x = x;
		this.y = y;
		visible = true;
	}

	protected Image returnImage(String imagePath, int width, int height) {
		this.width = width;
		this.height = height;
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		image = resizeImage(image, width, height);
		return image;
	}

	protected void loadImage(String imagePath, int width, int height) {
		this.width = width;
		this.height = height;
		try {
			image = ImageIO.read(new File(imagePath));
		} catch (IOException e) {
			e.printStackTrace();
		}

		image = resizeImage(image, width, height);
	}

	private Image resizeImage(Image image, int width, int height) {
		image = image.getScaledInstance(width, height, image.SCALE_DEFAULT);
		return image;
	}
	
	public Image getImage() {
		return image;
	}
	
	public void setX(int x) {
		this.x = x;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public int getWidth() {
		return width;
	}
	
	public int getHeight() {
		return height;
	}
	
	public boolean isVisible() {
		return visible;
	}
	
	public void setVisible(boolean visible) {
		this.visible = visible;
	}
	
	public Rectangle getBounds() {
		return new Rectangle(getX(), getY(), getWidth(), getHeight());
	}
}
