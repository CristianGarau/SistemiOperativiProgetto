
public class Pixel {
	private int alpha, red, green, blue;
	
	public Pixel() {
		
	}
	
	public Pixel(int alpha, int red, int green, int blue) {
		this.alpha = alpha;
		this.red = red;
		this.green = green;
		this.blue = blue;
	}

	public String toString() {
		return "A: " + alpha + " - R: " + red + " - G: " + green + " - B: " + blue;
	}
	
	public int getRed() {
		return red;
	}

	public void setRed(int red) {
		this.red = red;
	}

	public int getGreen() {
		return green;
	}

	public void setGreen(int green) {
		this.green = green;
	}

	public int getBlue() {
		return blue;
	}

	public void setBlue(int blue) {
		this.blue = blue;
	}

	public int getAlpha() {
		return alpha;
	}

	public void setAlpha(int alpha) {
		this.alpha = alpha;
	}
}
