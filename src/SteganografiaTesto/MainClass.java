package SteganografiaTesto;

import java.io.File;

import utils.Constants;
import utils.FileHandler;

public class MainClass {
	public static void main(String[] args) {
		// Cripto il messaggio nell'immagine, e poi la salvo
		String testo = "Sono un gattino";
		Image image = new Image(Constants.PATH_TO_INPUT+"gatto.png");

		image.encryptMessage(testo);
		image.saveImage(Constants.PATH_TO_OUTPUT+"gattoCriptato.png");

		// Decripto l'immagine e stampo il risultato.
		Image dec = new Image(Constants.PATH_TO_OUTPUT+"gattoCriptato.png");
		System.out.println(dec.decryptMessage());
		
		System.out.println();
		FileHandler fileHandler = FileHandler.getInstance();
		System.out.println(fileHandler.readTxtToString(Constants.SECRET_TEXT));
	}
}
