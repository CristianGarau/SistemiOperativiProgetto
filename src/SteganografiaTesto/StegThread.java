package SteganografiaTesto;

import java.util.List;

/**
 * 
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
	public synchronized void start() {
		run();
	}

	@Override
	public void run() {
		
		pixelList = Image.encryptMessage(message, pixelList);
		
	}
	
	public List<Pixel> getPixelList() {
		return pixelList;
	}
	
	public String getMessage() {
		return message;
	}

}
