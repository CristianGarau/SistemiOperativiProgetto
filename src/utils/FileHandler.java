package utils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

/**
 * Classe singleton da usare per gestire letture da file
 * 
 * @author Andrea
 *
 */
public class FileHandler {

	private FileHandler() {

	}
	
	public static FileHandler getInstance() {
		return new FileHandler();
	}
	
	public String readTxtToString(String path) {
		String res = null;
		BufferedReader buffReader;
		StringBuilder strBulder = new StringBuilder();

		try {
			int newLineCounter = 0;
			buffReader = new BufferedReader(new FileReader(path));
			String temp = null;
			while ((temp = buffReader.readLine()) != null ) {
				//TODO aggiungere algo per le andate a capo
				strBulder.append(temp);
				
			}
			res = strBulder.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return res;
	}
	
}
