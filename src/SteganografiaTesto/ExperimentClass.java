package SteganografiaTesto;

import java.util.List;

import utils.Constants;

/**
 * Classe da usare per fare esperimenti, per provare cose
 * @author Andrea
 *
 */
public class ExperimentClass {

	public static void main(String[] args) {

		Image img = new Image(Constants.DOG_IMAGE);
		
		List<List<Pixel>> sectionedImage = img.splitImageIn();
		
		String message = "Ciao, sto provando il metodo di split del testo";
		List<String> pieceOfMessage = img.splitMessage(message);
		
		StegThread thread1 =new StegThread(pieceOfMessage.get(0), sectionedImage.get(0));
		StegThread thread2 =new StegThread(pieceOfMessage.get(1), sectionedImage.get(1));
		StegThread thread3 =new StegThread(pieceOfMessage.get(2), sectionedImage.get(2));
		StegThread thread4 =new StegThread(pieceOfMessage.get(3), sectionedImage.get(3));
		
		thread1.start();
		thread2.start();
		thread3.start();
		thread4.start();
		
		String piece1 = Image.decryptMessage(thread1.getPixelList());
		String piece2 = Image.decryptMessage(thread2.getPixelList());
		String piece3 = Image.decryptMessage(thread3.getPixelList());
		String piece4 = Image.decryptMessage(thread4.getPixelList());
		
		
	}

}
