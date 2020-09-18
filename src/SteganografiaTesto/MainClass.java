package SteganografiaTesto;

import utils.Constants;
import utils.FileHandler;

public class MainClass {
	public static void main(String[] args) {
		FileHandler fileHandler = FileHandler.getInstance();
		System.out.println();

		// Cripto il messaggio nell'immagine, e poi la salvo
//		String testo = fileHandler.readTxtToString(Constants.SECRET_TEXT);
		String testo = fileHandler.readTxtToString(Constants.SECRET2_TEXT);
//		String testo = "Sono un gattino";
		Image image = new Image(Constants.PATH_TO_INPUT+"gatto.png");

		image.encryptMessage(testo);
		image.saveImage(Constants.PATH_TO_OUTPUT+"gattoCriptato.png");

		// Decripto l'immagine e stampo il risultato.
		Image dec = new Image(Constants.PATH_TO_OUTPUT+"gattoCriptato.png");
		System.out.println(dec.decryptMessage());
		
		System.out.println();
	}
}
