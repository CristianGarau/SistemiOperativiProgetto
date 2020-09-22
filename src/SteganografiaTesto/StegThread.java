package SteganografiaTesto;

import java.util.List;

/**
 * Thread dedicato alla crittazione di un messaggio all'interno di una lista di {@link Pixel}
 * @author Andrea, Garau
 *
 */
public class StegThread extends Thread {

	private List<Pixel> pixelList;
	private String message;
	
	public StegThread(String message, List<Pixel> pixelList) {
		this.pixelList = pixelList;
		this.message = message;
	}

	@Override
	public void run() {
//		System.out.println(getId() + " inizio");
		pixelList = Image.encryptMessage(message, pixelList);
//		System.out.println(getId() + " fine");
	}
	
	public List<Pixel> getPixelList() {
		return pixelList;
	}
	
	public String getMessage() {
		return message;
	}

}
