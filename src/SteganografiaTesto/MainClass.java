package SteganografiaTesto;

import utils.Constants;

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
	}
}
