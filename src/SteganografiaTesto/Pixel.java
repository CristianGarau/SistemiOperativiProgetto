package SteganografiaTesto;

public class Pixel {
	// Salvo la codifica binaria dei byte in delle stringhe, semplificandone la
	// modifica.
	private String alpha, red, green, blue;

	// Definisco una classe Pixel in modo da semplificare alcune operazioni

	public Pixel(int alpha, int red, int green, int blue) {
		// Converto l'int in una stringa binaria.
		// Se la stringa è più corta di 8 bit, aggiungo degli zeri per rendere tutto
		// della stessa
		// lunghezza. Ripeto l'operazione per le quattro componenti del pixel.
		int len;

		this.alpha = Integer.toBinaryString(alpha);
		len = this.alpha.length();
		if (len < 8) {
			for (int i = 0; i < 8 - len; i++) {
				this.alpha = "0" + this.alpha;
			}
		}

		this.red = Integer.toBinaryString(red);
		len = this.red.length();
		if (len < 8) {
			for (int i = 0; i < 8 - len; i++) {
				this.red = "0" + this.red;
			}
		}

		this.green = Integer.toBinaryString(green);
		len = this.green.length();
		if (len < 8) {
			for (int i = 0; i < 8 - len; i++) {
				this.green = "0" + this.green;
			}
		}

		this.blue = Integer.toBinaryString(blue);
		len = this.blue.length();
		if (len < 8) {
			for (int i = 0; i < 8 - len; i++) {
				this.blue = "0" + this.blue;
			}
		}
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
		// componenti
		// del pixel, partendo da alpha, poi red, green, blue.

		// Converto in codifica binaria il carattere.
		String bin = Integer.toBinaryString(c);

		char[] buffer;
		int len = bin.length();

		// Se la codifica è più corta di 8, aggiungo degli zeri.
		if (len < 8) {
			for (int i = 0; i < 8 - len; i++) {
				bin = "0" + bin;
			}
		}

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
