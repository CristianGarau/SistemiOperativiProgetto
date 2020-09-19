package SteganografiaTesto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.imageio.ImageIO;
import javax.swing.text.html.HTMLDocument.HTMLReader.IsindexAction;

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
	public List<List<Pixel>> splitImageIn() {
		List<List<Pixel>> sectionList = new ArrayList<List<Pixel>>();
		
		int numSec = 4;
		int lenPixelList = pixelList.size();
		int remain = lenPixelList % numSec;
		int lenSec = lenPixelList / numSec;
		
		for (int i = 0; i < numSec; i++) {
			int startingIndex = i*lenSec;
			int endingIndex = startingIndex + lenSec;
			
			if(i == (numSec-1) && remain != 0) {
				startingIndex = i * lenSec;
				endingIndex = startingIndex + remain;
				
			}
			// creo un nuovo arraylist cosi' la subList risulta modificabile
			// se non facessi in questo modo e prendessi solo la subList mi ritroverei con
			// una view, quindi una lista NON modificabile
			ArrayList<Pixel> splittedPixelList = new ArrayList<Pixel>(pixelList.subList(startingIndex, endingIndex));
			sectionList.add(splittedPixelList);
		}
		
		return sectionList;
	}
	
	public List<String> splitMessage(String message) {
		List<String> pieceOfMessage = new ArrayList<String>();
		
		int numOfPiece = 4;
		int lenMess = message.length();
		int remain = lenMess % 4;
		
		for (int i = 0; i < numOfPiece; i++) {
			int startingIndex = i*numOfPiece;
			int endingIndex = startingIndex + numOfPiece;
			
			if(i == (numOfPiece - 1) && remain != 0) {
				startingIndex = i * numOfPiece;
				// il +1 serve perche' nel metodo delle stringe l'ultimo index e' escluso
				endingIndex = startingIndex + remain + 1;
			}
			pieceOfMessage.add(message.substring(startingIndex, endingIndex));
		}
	
		return pieceOfMessage;
	
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
		if(!isEncryptable(message, pixelList)) {
			System.err.println("C'è un problema, il messaggio che si sta cercando di codificare è troppo lungo");
			throw new IllegalArgumentException();
		}
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

		if(!isEncryptable(message, pixelList)) {
			System.err.println("C'è un problema, il messaggio che si sta cercando di codificare è troppo lungo");
			throw new IllegalArgumentException();
		}
		//Faccio un ciclo sulla stringa, in modo da criptare un carattere alla volta
		//L'n-esimo carattere corrisponde all'n-esimo pixel nella lista
		for (int i = 0; i < message.length(); i++) {
			pixelList.get(i).encryptChar(message.charAt(i));
		}
		//Dopo aver criptato l'ultimo carattere, aggiungo il carattere end of text
		pixelList.get(message.length()).encryptETX();
	}
	
	/**
	 * Metodo ausiliario, serve a dire se il messaggio passato fra i parametri si riesce a cryptare 
	 * all'interno della pixelList nei parametri
	 * @param message
	 * @param pixelsAvailable
	 * @return true se cryptabile, false altrimenti
	 */
	private static boolean isEncryptable(String message, List<Pixel> pixelsAvailable) {
		boolean isEncryptable = false;
		
		if (message.length() <= pixelsAvailable.size()) {
			isEncryptable = true;
		}
			
		return isEncryptable;
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
