package SteganografiaTesto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import javax.imageio.ImageIO;

// Classe utilizzata per gestire le operazioni riguardanti le immagini

public class Image {
	BufferedImage img;
	File f;
	private int width;
	private int height;
	private ArrayList<Pixel> pixelList;

	public Image(String filePath) {
		try {
			// Inizializzo l'immagine, e salvo tutti i pixel in una lista
			f = new File(filePath);
			img = ImageIO.read(f);

			width = img.getWidth();
			height = img.getHeight();

			pixelList = getPixelList();
		} catch (IOException e) {
			System.out.println(e);
		}
	}

	// Restituisce un pixel dell'immagine, sottoforma di oggetto Pixel
	public Pixel getPixel(int x, int y) {

		int pixelData = img.getRGB(x, y);

		// pixelData è un int, faccio delle traslazioni bitwise e ottengo i valori
		// singoli
		int alpha = (pixelData >> 24) & 0xff;
		int red = (pixelData >> 16) & 0xff;
		int green = (pixelData >> 8) & 0xff;
		int blue = (pixelData) & 0xff;

		Pixel p = new Pixel(alpha, red, green, blue);

		return p;
	}

	// Prendo i pixel dell'immagine e li metto dentro una lista
	public ArrayList<Pixel> getPixelList() {
		ArrayList<Pixel> list = new ArrayList();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				list.add(getPixel(i, j));
			}
		}

		return list;
	}

	// Salva l'immagine dato il nome del file di output
	public void saveImage(String outputFilePath) {
		BufferedImage bi = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);

		int cont = 0;

		// Prendo la lista dei pixel e li organizzo secondo una griglia formata da width
		// e height
		for (int i = 0; i < this.width; i++) {
			for (int j = 0; j < this.height; j++) {
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

	// Cripta il messaggio data una stringa
	public void encryptMessage(String message) {
		// Faccio un ciclo sulla stringa, in modo da criptare un carattere alla volta
		// L'n-esimo carattere corrisponde all'n-esimo pixel nella lista
		for (int i = 0; i < message.length(); i++) {
			pixelList.get(i).encryptChar(message.charAt(i));
		}
		// Dopo aver criptato l'ultimo carattere, aggiungo il carattere end of text
		pixelList.get(message.length()).encryptETX();
	}

	// Decripta un eventuale messaggio nascosto.
	public String decryptMessage() {
		// TODO
		String message = "";
		char buf;

		// Decripto i pixel della lista, fino a trovare il carattere ETX
		for (int i = 0; i < pixelList.size(); i++) {
			buf = pixelList.get(i).decrypt();
			// Se è uguale a ETX esco, altrimenti aggiungo il carattere
			if (buf == (char) Integer.parseInt("00000011", 2)) {
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
