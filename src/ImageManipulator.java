import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;

import javax.imageio.ImageIO;

public class ImageManipulator {

	BufferedImage img;
	File f;
	private int width;
	private int height;
	private String outputFilePath;

	public ImageManipulator(String filePath) {
		outputFilePath = "src/output.png";
		try {
			f = new File(filePath);
			img = ImageIO.read(f);
		} catch (IOException e) {
			System.out.println(e);
		}
		width = img.getWidth();
		height = img.getHeight();
	}

	public Pixel getPixel(int x, int y) {
		int pixelData = img.getRGB(x, y);
		
		int alpha = (pixelData >> 24) & 0xff;
		int red = (pixelData >> 16) & 0xff;
		int green = (pixelData >> 8) & 0xff;
		int blue = (pixelData) & 0xff;
		
		return new Pixel(alpha, red, green, blue);
	}
	
	public Pixel getPixel(int x, int y, BufferedImage image) {
		int pixelData = image.getRGB(x, y);
		
		int alpha = (pixelData >> 24) & 0xff;
		int red = (pixelData >> 16) & 0xff;
		int green = (pixelData >> 8) & 0xff;
		int blue = (pixelData) & 0xff;
		
		return new Pixel(alpha, red, green, blue);
	}

	public void setPixel(int x, int y, Pixel pixel) {
		int pixelData = (pixel.getAlpha()<<24) | (pixel.getRed()<<16) | 
						(pixel.getGreen()<<8) | (pixel.getBlue());

		img.setRGB(x, y, pixelData);
	}
	
	public void hide(int x, int y, int toHide[]) {
		/*
		posso nascondere 4 bit ogni pixel
		0 = alpha
		1 = red
		2 = green
		3 = blue
		*/
		Pixel pixel = getPixel(x, y);
		
		if(toHide[0] == 0) {
			pixel.setAlpha(pixel.getAlpha() & ~(1 << 0));
		}else {
			pixel.setAlpha(pixel.getAlpha() | (1 << 0));
		}
		
		if(toHide[1] == 0) {
			pixel.setRed(pixel.getRed() & ~(1 << 0));
		}else {
			pixel.setRed(pixel.getRed() | (1 << 0));
		}
		
		if(toHide[2] == 0) {
			pixel.setGreen(pixel.getGreen() & ~(1 << 0));
		}else {
			pixel.setGreen(pixel.getGreen() | (1 << 0));
		}
		
		if(toHide[3] == 0) {
			pixel.setBlue(pixel.getBlue() & ~(1 << 0));
		}else {
			pixel.setBlue(pixel.getBlue() | (1 << 0));
		}

		setPixel(x, y, pixel);
	}
	
	public int decryptPixel(int x, int y, BufferedImage image){
		Pixel pixel = getPixel(x, y, image);
		int bitA = pixel.getAlpha() & 1;
		int bitR = pixel.getRed() & 1;
		int bitG = pixel.getGreen() & 1;
		int bitB = pixel.getBlue() & 1;
		
		return (bitA) | (bitR<<1) | (bitG<<2) | (bitB<<3);
	}
	
	public void saveImage() {
		try {
			ImageIO.write(img, "png", new File(outputFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public int[] intToArray(int input) {
		//Uso questa funzione per comodità per convertire
		//un numero in un array di 4 bit
		
	    int[] bits = new int[4];
	    for (int i = 3; i >= 0; i--) {
	        if((input & (1 << i)) != 0){
	        	bits[i] = 1;
	        }else {
	        	bits[i] = 0;
	        }
	    }

		return bits;
		
	}
	
	public void encryptImage() {
		BufferedImage toEncrypt = null;
		try {
			toEncrypt = ImageIO.read(new File("src/toEncrypt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		//uso i primi 6 pixel per salvare i valori di width e height
		int width = toEncrypt.getWidth();
		int height = toEncrypt.getHeight();

		this.hide(0, 0, intToArray(width));
		this.hide(1, 0, intToArray(width>>4));
		this.hide(2, 0, intToArray(width>>8));
		this.hide(3, 0, intToArray(height));
		this.hide(4, 0, intToArray(height>>4));
		this.hide(5, 0, intToArray(height>>8));
		
		try {
			ImageIO.write(img, "png", new File(outputFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void decrpytImage() {
		BufferedImage toDecrypt = null;
		try {
			toDecrypt = ImageIO.read(new File("src/output.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int width, height;
		
		width = decryptPixel(0, 0, toDecrypt) | decryptPixel(1, 0, toDecrypt)<<4 | decryptPixel(2, 0, toDecrypt)<<8;
		height = decryptPixel(3, 0, toDecrypt) | (decryptPixel(4, 0, toDecrypt)<<4) | (decryptPixel(5, 0, toDecrypt)<<8);
	}
	
	public BufferedImage getImg() {
		return img;
	}

	public void setImg(BufferedImage img) {
		this.img = img;
	}

	public File getF() {
		return f;
	}

	public void setF(File f) {
		this.f = f;
	}

	public int getWidth() {
		return width;
	}

	public void setWidth(int width) {
		this.width = width;
	}

	public int getHeight() {
		return height;
	}

	public void setHeight(int height) {
		this.height = height;
	}

}
