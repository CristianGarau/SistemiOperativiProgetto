package SteganografiaTesto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

public class Image {
	BufferedImage img;
	File f;
	private int width;
	private int height;
	private ArrayList<Pixel> pixelList;
	
	public Image(String filePath) {
		try {
			f = new File(filePath);
			img = ImageIO.read(f);
			
			width = img.getWidth();
			height = img.getHeight();
			
			pixelList = getPixelList();
		} catch (IOException e) {
			System.out.println(e);
		}
	}
	
	public Pixel getPixel(int x, int y) {
		int pixelData = img.getRGB(x, y);

		int alpha = (pixelData >> 24) & 0xff;
		int red = (pixelData >> 16) & 0xff;
		int green = (pixelData >> 8) & 0xff;
		int blue = (pixelData) & 0xff;

		Pixel p = new Pixel(alpha, red, green, blue);
		
		return p;
	}
	
	public ArrayList<Pixel> getPixelList(){
		ArrayList<Pixel> list = new ArrayList();
		
		for(int i = 0; i<width; i++) {
			for(int j = 0; j<height; j++) {
				list.add(getPixel(i, j));
			}
		}
		
		return list;
	}

	public void saveImage(String outputFilePath) {
		BufferedImage bi = new BufferedImage(this.width, this.height,
                BufferedImage.TYPE_INT_ARGB);
		
		int cont = 0;
		
		for(int i = 0; i<this.width; i++) {
			for (int j = 0; j<this.height; j++) {
				bi.setRGB(i, j, pixelList.get(cont).getRGB(i, j));
				cont++;
			}
		}
		
		try {
			ImageIO.write(bi, "png", new File(outputFilePath));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public void encryptMessage(String message) {
		for(int i = 0; i<message.length(); i++) {
			pixelList.get(i).encryptChar(message.charAt(i));
		}
		pixelList.get(message.length()).encryptETX();
	}
	
	public String decryptMessage() {
		//TODO
		String message="";
		char buf;

		for(int i = 0; i<pixelList.size(); i++) {
			buf = pixelList.get(i).decrypt();
			//Se è uguale a ETX esco, altrimenti aggiungo il carattere
			if(buf == (char)Integer.parseInt("00000011", 2)) {
				break;
			}
			message += buf;
		}
		return message;
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
