
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int x = 0;
		int y = 0;
		ImageManipulator img = new ImageManipulator("src/input.png");
		/*
		for(int i = 0; i<img.getWidth(); i++) {
			for(int j=0; j<img.getHeight(); j++) {
				System.out.println(img.getPixel(j, i).toString());
			}
			System.out.println("Fine Riga numero " + i);
		}
		//Provo a impostare il primo pixel al colore rosso
		img.setPixel(x, y, new Pixel(100, 255,0, 0));
		img.saveImage();
		
		//Testo come nascondere 4 bit nel primo pixel
		int[] toHide = {0, 0, 0, 0};
		System.out.println("Valore prima: " + img.getPixel(x, y));
		img.hide(x, y, toHide);
		System.out.println("Valore dopo: " + img.getPixel(x, y));
		img.saveImage();
		*/
		img.encryptImage();

		img.decryptImage();
	}

}
