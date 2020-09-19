package SteganografiaTesto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import javax.imageio.ImageIO;

import utils.Constants;

/**
 *  Classe utilizzata per gestire le operazioni riguardanti le immagini
 * @author Garau
 *
 */

public class Image {
	private BufferedImage img;
	private File f;
	private int width;
	private int height;
	private List<Pixel> pixelList;

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
	
	/**
	 * Metodo da usare per dividere l'immagine in 4 colonne, le quali verranno singolarmente
	 * gestite da thread dedicati.
	 * 
	 * @return Una lista di liste, ovvero una lista avente per elemento una lista di pixel
	 */
	private ArrayList<ArrayList<Pixel>> splitImageIn() {
		ArrayList<ArrayList<Pixel>> sectionList = new ArrayList<ArrayList<Pixel>>();
		
		int numSec = 4;
		int lenPixelList = this.pixelList.size();
		int remain = lenPixelList % numSec;
		int lenSec = lenPixelList / numSec;
		
		for (int i = 0; i < numSec; i++) {
			int startingIndex = i*lenSec;
			int endingIndex = startingIndex + lenSec;
			
			if(i == (numSec-1) && remain != 0) {
				startingIndex = i * lenSec;
				endingIndex = startingIndex + remain;
				
			}
			// creo un nuovo arraylist cosi' sono sicuro che sara' modificabile
			ArrayList<Pixel> splittedPixelList = new ArrayList<Pixel>(this.pixelList.subList(startingIndex, endingIndex));
			sectionList.add(splittedPixelList);
		}
		
		return sectionList;
	}
	
	/**
	 * Restituisce un pixel dell'immagine, sottoforma di oggetto Pixel
	 * @param x
	 * @param y
	 * @return
	 */
	private Pixel getPixel(int x, int y) {
		
		int pixelData = img.getRGB(x, y);

		//pixelData è un int, faccio delle traslazioni bitwise e ottengo i valori singoli
		int alpha = (pixelData >> 24) & 0xff;
		int red = (pixelData >> 16) & 0xff;
		int green = (pixelData >> 8) & 0xff;
		int blue = (pixelData) & 0xff;

		Pixel p = new Pixel(alpha, red, green, blue);

		return p;
	}

	/**
	 * Prendo i pixel dell'immagine e li metto dentro una lista
	 * @return
	 */
	private List<Pixel> getPixelList() {
		// TODO aggiungere metodo per suddivisione in 4 parti
		ArrayList<Pixel> list = new ArrayList<Pixel>();

		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
				list.add(getPixel(i, j));
			}
		}

		return list;
	}

	/**
	 * Salva l'immagine dato il nome del file di output
	 * @param outputFilePath
	 */
	public void saveImage(String outputFilePath) {
		BufferedImage bi = new BufferedImage(this.width, this.height, BufferedImage.TYPE_INT_ARGB);

		int cont = 0;

		//Prendo la lista dei pixel e li organizzo secondo una griglia formata da width e height
		//viene fatto a colonne, quindi a strisce verticali
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

	/**
	 * Cripta il messaggio data una stringa. Non supporta il multithread.
	 * @param message
	 */
	public void encryptMessage(String message) {
		//Faccio un ciclo sulla stringa, in modo da criptare un carattere alla volta
		//L'n-esimo carattere corrisponde all'n-esimo pixel nella lista
		for (int i = 0; i < message.length(); i++) {
			pixelList.get(i).encryptChar(message.charAt(i));
		}
		//Dopo aver criptato l'ultimo carattere, aggiungo il carattere end of text
		pixelList.get(message.length()).encryptETX();
	}
	
	/**
	 * Cripta il messaggio data una stringa. Supporta il multithread.
	 * 
	 * @param message
	 */
	public static void encryptMessage(String message, List<Pixel> pixelList) {
		//Faccio un ciclo sulla stringa, in modo da criptare un carattere alla volta
		//L'n-esimo carattere corrisponde all'n-esimo pixel nella lista
		for (int i = 0; i < message.length(); i++) {
			pixelList.get(i).encryptChar(message.charAt(i));
		}
		//Dopo aver criptato l'ultimo carattere, aggiungo il carattere end of text
		pixelList.get(message.length()).encryptETX();
	}

	/**
	 * Decripta un eventuale messaggio nascosto.
	 * @return
	 */
	public String decryptMessage() {
		// TODO da rendere synchronized
		String message = "";
		char buf;

		//Decripto i pixel della lista, fino a trovare il carattere ETX
		for (int i = 0; i < pixelList.size(); i++) {
			buf = pixelList.get(i).decrypt();
			// Se è uguale a ETX esco, altrimenti aggiungo il carattere
			if (buf == Constants.ETX_ASCII_CHAR) {
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
