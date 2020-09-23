package SteganografiaTesto;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
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
	
	public Image(int width, int heigh, List<Pixel> pixelList) {
		this.width = width;
		this.height = heigh;
		this.pixelList = pixelList;
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
			
			if(i == (numSec - 1) && remain != 0) {
				endingIndex += remain + lenSec;
			}
			
			// creo un nuovo arraylist cosi' la subList risulta modificabile
			// se non facessi in questo modo e prendessi solo la subList mi ritroverei con
			// una view, quindi una lista NON modificabile
			List<Pixel> splittedPixelList = new ArrayList<Pixel>(pixelList.subList(startingIndex, endingIndex));
			
			sectionList.add(splittedPixelList);
		}
		
		return sectionList;
	}
	
	/**
	 * Metodo che serve per combinare più liste di pixel in una immagine sola. Oltretutto la salva 
	 * nel path dato come parametro
	 * @param pieceOfImage
	 */
	public static void composeImage(List<List<Pixel>> pieceOfImage, String outputFilePath, int width, int height) {
		List<Pixel> combinedImage = new ArrayList<Pixel>();
		
		for (List<Pixel> elem : pieceOfImage) {
			combinedImage.addAll(elem);
		}
		
		Image.saveImage(combinedImage, outputFilePath, width, height);
	}
	
	public List<String> splitMessage(String message) {
		List<String> pieceOfMessage = new ArrayList<String>();
		
		int numOfPiece = 4;
		int lenMess = message.length();
		int lenPiece = lenMess / numOfPiece;
		int remain = lenMess % numOfPiece;
		
		for (int i = 0; i < numOfPiece; i++) {
			int startingIndex = i*lenPiece;
			int endingIndex = startingIndex + lenPiece;
		
			// se il numero di pixel non è divisibile per 4 allora
			// all'ora all'ultimo giro non aggiungo solo il resto, 
			// non tutto lenMess
			if(i == (numOfPiece - 1) && remain != 0) {
				endingIndex = startingIndex + lenPiece + remain;
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
	public List<Pixel> getPixelList() {

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
	 * Salva un'immagine data una lista di pixel, altezza e larghezza della foto
	 * @param pixelList
	 * @param outputFilePath
	 */
	public static void saveImage(List<Pixel> pixelList, String outputFilePath, int width, int height) {
		
		BufferedImage bi = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);

		int cont = 0;

		//Prendo la lista dei pixel e li organizzo secondo una griglia formata da width e height
		//viene fatto a colonne, quindi a strisce verticali
		for (int i = 0; i < width; i++) {
			for (int j = 0; j < height; j++) {
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
	 * @return the pixel list with the message encrypted inside
	 */
	public static List<Pixel> encryptMessage(String message, List<Pixel> pixelList) {

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
		return pixelList;
	}
	
	public String decryptMessageMultiThread() {
		String res = null;
		StringBuilder sb = new StringBuilder();
		
		List<List<Pixel>> splittedImage = this.splitImageIn();

		for (List<Pixel> elem : splittedImage) {
			String pieceOfMessage = Image.decryptMessage(elem);
			sb.append(pieceOfMessage);
		}
		res = sb.toString();
		return res;
	}
	
	
	/**
	 * Metodo ausiliario, serve a dire se il messaggio passato fra i parametri si riesce a criptare 
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
	
	/**
	 * Metodo per decriptare un messaggio che era stato criptato con l'algoritmo usato da {@link Image#encryptMessage(String, List)}}a partire dalla 
	 * @param pixelList
	 * @return
	 */
	public static String decryptMessage(List<Pixel> pixelList) {
		
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
