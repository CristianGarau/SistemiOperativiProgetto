
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImageManipulator img = new ImageManipulator("src/input.png");
		
		for(int i = 0; i<img.getWidth(); i++) {
			for(int j=0; j<img.getHeight(); j++) {
				System.out.println(img.getPixel(j, i).toString());
			}
			System.out.println("Fine Riga numero " + i);
		}
		//Provo a impostare il primo pixel al colore rosso
		img.setPixel(0, 0, new Pixel(0,255,0,255));
		img.saveImage();
	}

}
