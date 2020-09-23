package SteganografiaTesto;

import utils.Constants;
import utils.FileHandler;

public class MonoThreadCrypting {
	public static void main(String[] args) {
		FileHandler fileHandler = FileHandler.getInstance();

		// Cripto il messaggio nell'immagine, e poi la salvo
		String testo = fileHandler.readTxtToString(Constants.SECRET_TEXT);

		Image img = new Image(Constants.DOG_IMAGE);
		img.encryptMessage(testo);		
		img.saveImage(Constants.PATH_TO_OUTPUT+"caneCriptato.png");
		
		// Decripto l'immagine e stampo il risultato.
		Image dec = new Image(Constants.PATH_TO_OUTPUT+"caneCriptato.png");
		String decMessage = dec.decryptMessage();
		
		System.out.println(decMessage);
		

	}
}
