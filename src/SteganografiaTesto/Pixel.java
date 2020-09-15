package SteganografiaTesto;

public class Pixel {
	private String alpha, red, green, blue;
	
	public Pixel(int alpha, int red, int green, int blue) {
		int len;
		
		this.alpha = Integer.toBinaryString(alpha);
		len = this.alpha.length();
		if(len<8) {
			for(int i = 0; i<8-len; i++) {
				this.alpha = "0" + this.alpha;
			}
		}
		
		this.red = Integer.toBinaryString(red);
		len = this.red.length();
		if(len<8) {
			for(int i = 0; i<8-len; i++) {
				this.red = "0" + this.red;
			}
		}
		
		this.green = Integer.toBinaryString(green);
		len = this.green.length();
		if(len<8) {
			for(int i = 0; i<8-len; i++) {
				this.green = "0" + this.green;
			}
		}
		
		this.blue = Integer.toBinaryString(blue);
		len = this.blue.length();
		if(len<8) {
			for(int i = 0; i<8-len; i++) {
				this.blue = "0" + this.blue;
			}
		}
	}

	public char decrypt() {

		String buf="";

		buf+= alpha.charAt(6);
		buf+= alpha.charAt(7);
		
		buf+= red.charAt(6);
		buf+= red.charAt(7);
		
		buf+= green.charAt(6);
		buf+= green.charAt(7);
		
		buf+= blue.charAt(6);
		buf+= blue.charAt(7);
		
		return (char)Integer.parseInt(buf, 2);
	}
	
	public void encryptChar(char c) {
		String bin = Integer.toBinaryString(c);

		char[] buffer;
		int len= bin.length();
		
		if(len<8) {
			for(int i = 0; i<8-len; i++) {
				bin = "0" + bin;
			}
		}
		
		buffer = alpha.toCharArray();
		buffer[6] = bin.charAt(0);
		buffer[7] = bin.charAt(1);
		this.alpha = String.valueOf(buffer);
		
		buffer = red.toCharArray();
		buffer[6] = bin.charAt(2);
		buffer[7] = bin.charAt(3);
		this.red = String.valueOf(buffer);
		
		buffer = green.toCharArray();
		buffer[6] = bin.charAt(4);
		buffer[7] = bin.charAt(5);
		this.green = String.valueOf(buffer);
		
		buffer = blue.toCharArray();
		buffer[6] = bin.charAt(6);
		buffer[7] = bin.charAt(7);
		this.blue = String.valueOf(buffer);
	}
	
	public void encryptETX() {
		encryptChar((char)Integer.parseInt("00000011", 2)); //End of text, ETX
	}
	
	public int getRGB(int i, int j) {
		return Integer.parseInt(alpha, 2)<<24 | Integer.parseInt(red, 2)<<16 | Integer.parseInt(green, 2) <<8 | Integer.parseInt(blue, 2);
	}
	
	public String toString() {
		return "A: " + Integer.parseInt(alpha, 2) + " - R: " + Integer.parseInt(red, 2) + " - G: " + Integer.parseInt(green, 2) + " - B: " + Integer.parseInt(blue, 2);
	}
	
	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	public String getGreen() {
		return green;
	}

	public void setGreen(String green) {
		this.green = green;
	}

	public String getBlue() {
		return blue;
	}

	public void setBlue(String blue) {
		this.blue = blue;
	}

	
	
	
}
