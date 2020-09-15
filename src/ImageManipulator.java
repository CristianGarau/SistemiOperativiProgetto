import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
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
		int pixelData = (pixel.getAlpha() << 24) | (pixel.getRed() << 16) | (pixel.getGreen() << 8) | (pixel.getBlue());

		img.setRGB(x, y, pixelData);
	}

	public void hide(int x, int y, int toHide[]) {
		/*
		 * posso nascondere 4 bit ogni pixel 0 = alpha 1 = red 2 = green 3 = blue
		 */
		Pixel pixel = getPixel(x, y);

		if (toHide[0] == 0) {
			pixel.setAlpha(pixel.getAlpha() & ~(1 << 0));
		} else {
			pixel.setAlpha(pixel.getAlpha() | (1 << 0));
		}

		if (toHide[1] == 0) {
			pixel.setRed(pixel.getRed() & ~(1 << 0));
		} else {
			pixel.setRed(pixel.getRed() | (1 << 0));
		}

		if (toHide[2] == 0) {
			pixel.setGreen(pixel.getGreen() & ~(1 << 0));
		} else {
			pixel.setGreen(pixel.getGreen() | (1 << 0));
		}

		if (toHide[3] == 0) {
			pixel.setBlue(pixel.getBlue() & ~(1 << 0));
		} else {
			pixel.setBlue(pixel.getBlue() | (1 << 0));
		}

		setPixel(x, y, pixel);
	}

	public int decryptPixel(int x, int y, BufferedImage image) {
		Pixel pixel = getPixel(x, y, image);
		int bitA = pixel.getAlpha() & 1;
		int bitR = pixel.getRed() & 1;
		int bitG = pixel.getGreen() & 1;
		int bitB = pixel.getBlue() & 1;

		return (bitA) | (bitR << 1) | (bitG << 2) | (bitB << 3);
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
		// Uso questa funzione per comodità per convertire
		// un numero in un array di 4 bit

		int[] bits = new int[4];
		for (int i = 3; i >= 0; i--) {
			if ((input & (1 << i)) != 0) {
				bits[i] = 1;
			} else {
				bits[i] = 0;
			}
		}

		return bits;

	}

	public void encryptImage() {
		BufferedImage toEncrypt = null;
		boolean flag = true;
		try {
			toEncrypt = ImageIO.read(new File("src/toEncrypt.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// uso i primi 6 pixel per salvare i valori di width e height
		int encWidth = toEncrypt.getWidth();
		int encHeight = toEncrypt.getHeight();
		int pixelValue;
		int count = 0;

		this.hide(0, 0, intToArray(encWidth));
		this.hide(1, 0, intToArray(encWidth >> 4));
		this.hide(2, 0, intToArray(encWidth >> 8));
		this.hide(3, 0, intToArray(encHeight));
		this.hide(4, 0, intToArray(encHeight >> 4));
		this.hide(5, 0, intToArray(encHeight >> 8));

		// Inizio a criptare i pixel effettivi
		ArrayList<Integer> pixels = new ArrayList<>();
		for (int i = 0; i < encWidth; i++) {
			for (int j = 0; j < encHeight; j++) {
				pixelValue = toEncrypt.getRGB(j, i);
				pixels.add(pixelValue >> 28 & 0xf);
				pixels.add(pixelValue >> 24 & 0xf);
				pixels.add(pixelValue >> 20 & 0xf);
				pixels.add(pixelValue >> 16 & 0xf);
				pixels.add(pixelValue >> 12 & 0xf);
				pixels.add(pixelValue >> 8 & 0xf);
				pixels.add(pixelValue >> 4 & 0xf);
				pixels.add(pixelValue & 0xf);
			}
		}
		/*
		 * for(int i=20; i<24; i++) { System.out.println(pixels.get(i)); }
		 */
		System.out.println("Dimensione pixels da nascondere: " + pixels.size());
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				if (flag) {
					j = 6;
					flag = false;
				}
				if (count >= pixels.size()) {
					break;
				}
				System.out.println("Modifico pixel: " + j + "-" + i + " con: " + count + " - " + pixels.get(count));
				//this.hide(j, i, intToArray(pixels.get(count)));
				int[] data = {0, 0, 0, 0};
				this.hide(j,  i, data);
				count++;
			}
		}

		try {
			ImageIO.write(img, "png", new File(outputFilePath));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void decryptImage() {
		System.out.println();
		BufferedImage toDecrypt = null;
		BufferedImage decrypted = null;
		try {
			toDecrypt = ImageIO.read(new File("src/output.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		int decWidth, decHeight, limit;
		boolean flag = true;

		System.out.println(
				"L'immagine di output ha width: " + toDecrypt.getWidth() + "e height: " + toDecrypt.getHeight());

		decWidth = decryptPixel(0, 0, toDecrypt) | decryptPixel(1, 0, toDecrypt) << 4
				| decryptPixel(2, 0, toDecrypt) << 8;
		decHeight = decryptPixel(3, 0, toDecrypt) | (decryptPixel(4, 0, toDecrypt) << 4)
				| (decryptPixel(5, 0, toDecrypt) << 8);

		System.out.println("L'immagine decriptata ha width: " + decWidth + " e height: " + decHeight);

		decrypted = new BufferedImage(decWidth, decHeight, BufferedImage.TYPE_INT_ARGB);

		// inizio a leggere dal settimo pixel, essendo i primi 6 per le dimensioni
		// fino a (width*height*8) es: (4*4*8)=128 pixel

		limit = (decWidth * decHeight * 8) + 6;
		ArrayList<Integer> pixels = new ArrayList<>();
		for (int i = 0; i < toDecrypt.getWidth(); i++) {
			for (int j = 0; j < toDecrypt.getHeight(); j++) {
				if (flag) {
					j = 6;
					flag = false;
				}
				if (((i * toDecrypt.getWidth()) + j) < limit) {
					pixels.add(decryptPixel(i, j, toDecrypt));
					System.out.println("Aggiungo pixel " + i + "-" + j + " limit=" + limit);
				} else {
					break;
				}
			}
		}
		System.out.println();
		int count = 0;
		for (int i = 0; i < decWidth; i++) {
			for (int j = 0; j < decHeight; j++) {
				System.out.println("aggiungo a " + i + "-" + j + " il valore " + pixels.get(count) + " " + count);
				decrypted.setRGB(j, i,
						pixels.get(count) | pixels.get(count+1) << 4 | pixels.get(count+2) << 8 | pixels.get(count+3) << 12
								| pixels.get(count+4) << 16 | pixels.get(count+5) << 20 | pixels.get(count+6) << 24
								| pixels.get(count+7) << 28);
				System.out.println("---");
				for(int k = 0; k<8; k++) {
					System.out.println(pixels.get(count+k));
				}
				System.out.println("---");
				count = count + 8;
			}
		}

		File outputfile = new File("src/decrypted.png");
		try {
			ImageIO.write(decrypted, "png", outputfile);
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
