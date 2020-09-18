package SteganografiaTesto;

public class MainClass {
	public static void main(String[] args) {
		// Cripto il messaggio nell'immagine, e poi la salvo
		String testo = "Sono un gattino";
		Image image = new Image("src/gatto.png");

		image.encryptMessage(testo);
		image.saveImage("src/gattoCriptato.png");

		// Decripto l'immagine e stampo il risultato.
		Image dec = new Image("src/gattoCriptato.png");
		System.out.println(dec.decryptMessage());
	}
}
