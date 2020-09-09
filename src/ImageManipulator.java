import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

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
		int red = (pixelData >> 16) & 0xff;
		int green = (pixelData >> 8) & 0xff;
		int blue = (pixelData) & 0xff;
		int alpha = (pixelData >> 24) & 0xff;
		return new Pixel(red, green, blue, alpha);
	}

	public void setPixel(int x, int y, Pixel pixel) {
		int pixelData = (pixel.getAlpha()<<24) | (pixel.getRed()<<16) | 
						(pixel.getGreen()<<8) | pixel.getBlue();
		img.setRGB(x, y, pixelData);
	}
	
	public void saveImage() {
		try {
			ImageIO.write(img, "png", new File(outputFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
