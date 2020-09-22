package SteganografiaTesto;

import utils.Constants;
import utils.FileHandler;

public class MonoThreadCrypting {
	public static void main(String[] args) {
		FileHandler fileHandler = FileHandler.getInstance();

		// Cripto il messaggio nell'immagine, e poi la salvo
		//String testo = fileHandler.readTxtToString(Constants.SECRET_TEXT);
		String testo = fileHandler.readTxtToString(Constants.SECRET2_TEXT);

		Image image = new Image(Constants.DOG_IMAGE);
		long startTime = System.currentTimeMillis();
		image.encryptMessage(testo);
		long endTime   = System.currentTimeMillis();
		long totalTime = endTime - startTime;
		System.out.println("Tempo di esecuzione necessario alla crittazione: " + totalTime/1000.0);
		
		image.saveImage(Constants.PATH_TO_OUTPUT+"caneCriptato.png");
		System.out.println();
		
		// Decripto l'immagine e stampo il risultato.
		Image dec = new Image(Constants.PATH_TO_OUTPUT+"caneCriptato.png");
		startTime = System.currentTimeMillis();
		String decMessage = dec.decryptMessage();
		endTime   = System.currentTimeMillis();
		totalTime = endTime - startTime;
		
		System.out.println(decMessage);
		System.out.println("Tempo di esecuzione necessario alla decrittazione: " + totalTime/1000.0);
		

	}
}
