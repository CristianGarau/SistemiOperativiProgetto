package SteganografiaTesto;

import java.util.ArrayList;
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
		
		
		//StegThread thread1 =new StegThread(message, pixelList);
		
		
	}

}
