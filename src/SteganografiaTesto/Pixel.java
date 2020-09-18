package SteganografiaTesto;

/**
 * Classe che racchiude il concetto di pixel contiene le informazioni principali, ovvero i
 * valori alpha, red, green e blue.
 * 
 * @author Garau
 *
 */
public class Pixel {
	// Salvo la codifica binaria dei byte in delle stringhe, semplificandone la
	// modifica.
	private String alpha, red, green, blue;

	// Definisco una classe Pixel in modo da semplificare alcune operazioni
	public Pixel(int alpha, int red, int green, int blue) {
		// Converto l'int in una stringa binaria.
		// Se la stringa è più corta di 8 bit, aggiungo degli zeri per rendere tutto
		// della stessa lunghezza. 
		// Ripeto l'operazione per le quattro componenti del pixel.

		this.alpha = Integer.toBinaryString(alpha);
		this.alpha = makeEightBitValue(this.alpha);
		if(this.alpha.length() < 8)
			System.out.println("errore");

		this.red = Integer.toBinaryString(red);
		this.red = makeEightBitValue(this.red);
		if (this.red.length() < 8) {
			System.out.println("errore");
		}
		
		this.green = Integer.toBinaryString(green);
		this.green = makeEightBitValue(this.green);
		if (this.green.length() < 8) {
			System.out.println("errore");
		}
		
		this.blue = Integer.toBinaryString(blue);
		this.blue = makeEightBitValue(this.blue);
		if (this.blue.length() < 8) {
			System.out.println("errore");
		}
	}

	/**
	 * Metodo ausiliario per il costruttore della classe {@link Pixel}. <br>
	 * Controlla che la string value sia lunga 8 caratteri, se cosi' non e'
	 * si procede ad aggiungere degli zeri nelle prime posizioni. <br>
	 * Utile se value e' un binario scritto come una stringa, risulta quindi facile aggiungere degli zeri
	 * nelle posizioni piu' significative.
	 * 
	 * @param value
	 * @return
	 */
	private String makeEightBitValue(String value) {

		String res = value;
		int len = value.length();
		
		if (len < 8) {
			for (int i = 0; i < 8 - len; i++) {
				value = "0" + value;
			}
			res = value;
			
		} else if (len > 8) {
			System.err.println("Errore nel metodo makeEightBitValue, "
					+ "una stringa di bit è più lunga di 8 caratteri, quindi più lunga di 8 bit");
			
		}
		
		return res;
	}

	public char decrypt() {
		// Ricavo i due bit meno significativi di ogni componente del pixel
		// e li concateno, ottenendo un byte, quindi un carattere
		// partendo da alpha, poi red, green, e blue.

		String buf = "";

		buf += alpha.charAt(6);
		buf += alpha.charAt(7);

		buf += red.charAt(6);
		buf += red.charAt(7);

		buf += green.charAt(6);
		buf += green.charAt(7);

		buf += blue.charAt(6);
		buf += blue.charAt(7);

		// Converto prima in un intero la codifica binaria, e poi in char.
		return (char) Integer.parseInt(buf, 2);
	}

	public void encryptChar(char c) {
		// Cripto un carattere, scrivendo i suoi bit, nei bit meno significativi delle 4
		// componenti del pixel, partendo da alpha, poi red, green, blue.

		// Converto in codifica binaria il carattere.
		String bin = Integer.toBinaryString(c);

		char[] buffer;

		// Se la codifica è più corta di 8, aggiungo degli zeri.	
		bin = makeEightBitValue(bin);
		
		// Modifico i bit meno significativi (6 e 7), con i bit del carattere.
		// Mi appoggio su un buffer per semplicità, e poi riconverto in stringa.
		buffer = alpha.toCharArray();
		buffer[6] = bin.charAt(0);
		buffer[7] = bin.charAt(1);
		this.alpha = String.valueOf(buffer);

		buffer = red.toCharArray();
		buffer[6] = bin.charAt(2);
		buffer[7] = bin.charAt(3);
		this.red = String.valueOf(buffer);

		buffer = green.toCharArray();
		buffer[6] = bin.charAt(4);
		buffer[7] = bin.charAt(5);
		this.green = String.valueOf(buffer);

		buffer = blue.toCharArray();
		buffer[6] = bin.charAt(6);
		buffer[7] = bin.charAt(7);
		this.blue = String.valueOf(buffer);
	}

	// Usata per criptare il carattere ETX
	public void encryptETX() {
		encryptChar((char) Integer.parseInt("00000011", 2)); // End of text, ETX
	}

	// Restituisce il valore intero di un pixel, data la sua posizione
	// nell'immagine.
	public int getRGB(int i, int j) {
		return Integer.parseInt(alpha, 2) << 24 | Integer.parseInt(red, 2) << 16 | Integer.parseInt(green, 2) << 8
				| Integer.parseInt(blue, 2);
	}

	public String toString() {
		return "A: " + Integer.parseInt(alpha, 2) + " - R: " + Integer.parseInt(red, 2) + " - G: "
				+ Integer.parseInt(green, 2) + " - B: " + Integer.parseInt(blue, 2);
	}

	public String getAlpha() {
		return alpha;
	}

	public void setAlpha(String alpha) {
		this.alpha = alpha;
	}

	public String getRed() {
		return red;
	}

	public void setRed(String red) {
		this.red = red;
	}

	public String getGreen() {
		return green;
	}

	public void setGreen(String green) {
		this.green = green;
	}

	public String getBlue() {
		return blue;
	}

	public void setBlue(String blue) {
		this.blue = blue;
	}

}
