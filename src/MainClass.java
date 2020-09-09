
public class MainClass {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ImageManipulator img = new ImageManipulator("src/image16.jpg");
		
		for(int i = 0; i<img.getWidth(); i++) {
			for(int j=0; j<img.getHeight(); j++) {
				System.out.println(img.getPixel(j, i).toString());
			}
			System.out.println("Fine Riga");
		}
	}

}
