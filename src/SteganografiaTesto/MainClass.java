package SteganografiaTesto;

public class MainClass {
	public static void main(String[] args) {
		/*
		Pixel p = new Pixel(0, 255, 255, 255);
		
		System.out.println(p);
		
		p.encryptChar(' ');
		
		System.out.println(p);
		
		*/
		String testo="Sono un gatto";
		Image image = new Image("src/gatto.png");

		
		System.out.println(testo.length());
		
		image.encryptMessage(testo);
		image.saveImage("src/gattoCriptato.png");
		
		Image dec = new Image("src/gattoCriptato.png");
		System.out.println(dec.decryptMessage());
	}
}
