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
		
		Image.encryptMessage(message, pixelList);
			
		
	}

}
