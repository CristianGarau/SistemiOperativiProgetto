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
public class ExperimentClass {

	public static void main(String[] args) {

		Image img = new Image(Constants.DOG_IMAGE);
		FileHandler fileHandler = FileHandler.getInstance();

		List<List<Pixel>> sectionedImage = img.splitImageIn();
		// String message = "Ciao, sto provando il metodo di split del testo";
		String message = fileHandler.readTxtToString(Constants.SECRET2_TEXT);
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		String piece1 = Image.decryptMessage(thread1.getPixelList());
		String piece2 = Image.decryptMessage(thread2.getPixelList());
		String piece3 = Image.decryptMessage(thread3.getPixelList());
		String piece4 = Image.decryptMessage(thread4.getPixelList());

		
		System.out.println("Prima parte: " + piece1 + "\n");
		System.out.println("Seconda parte: " + piece2 + "\n");
		System.out.println("Terza parte: " + piece3 + "\n");
		System.out.println("Quarta parte: " + piece4 + "\n");
		// System.out.println(piece1+piece2+piece3+piece4);
	}

}
