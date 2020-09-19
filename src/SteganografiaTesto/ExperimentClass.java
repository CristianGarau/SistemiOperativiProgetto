package SteganografiaTesto;

import java.util.ArrayList;

import utils.Constants;

/**
 * Classe da usare per fare esperimenti, per provare cose
 * @author Andrea
 *
 */
public class ExperimentClass {

	public static void main(String[] args) {

		Image img = new Image(Constants.DOG_IMAGE);
		
		ArrayList<ArrayList<Pixel>> sectionedImage = img.splitImageIn();
		
		
		
	}

}
