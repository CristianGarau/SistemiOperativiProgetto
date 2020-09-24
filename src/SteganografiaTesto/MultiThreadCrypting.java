package SteganografiaTesto;

import java.util.List;

import utils.Constants;
import utils.FileHandler;

/**
 * Classe da usare per fare esperimenti, per provare cose
 * 
 * @author Andrea
 *
 */
public class MultiThreadCrypting {

	public static void main(String[] args) {

		Image img = new Image(Constants.DOG_IMAGE);
		FileHandler fileHandler = FileHandler.getInstance();

		List<List<Pixel>> sectionedImage = img.splitImageIn();
		// String message = "Ciao, sto provando il metodo di split del testo";
		String message = fileHandler.readTxtToString(Constants.SECRET_TEXT);
		List<String> pieceOfMessage = img.splitMessage(message);

		StegThread thread1 = new StegThread(pieceOfMessage.get(0), sectionedImage.get(0));
		StegThread thread2 = new StegThread(pieceOfMessage.get(1), sectionedImage.get(1));
		StegThread thread3 = new StegThread(pieceOfMessage.get(2), sectionedImage.get(2));
		StegThread thread4 = new StegThread(pieceOfMessage.get(3), sectionedImage.get(3));

		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		try {
			thread1.join();
			thread2.join();
			thread3.join();
			thread4.join();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		
		//Salvo la nuova immagine con i pixel modificati
		Image.composeImage(sectionedImage, Constants.PATH_TO_OUTPUT + "caneCriptato.png", img.getWidth(), img.getHeight());

		//Ricostruisco la stringa, concatenando i vari pezzi
		Image cryptedImg = new Image(Constants.PATH_TO_OUTPUT + "caneCriptato.png");
		
		String res = cryptedImg.decryptMessageMultiThread();
		System.out.println(res);
	}

}
