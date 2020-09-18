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
			buffReader = new BufferedReader(new FileReader(path));
			String temp = null;
			while ((temp = buffReader.readLine()) != null ) {
				strBulder.append(temp + "\n");

			}
			res = strBulder.toString();

		} catch (IOException e) {
			e.printStackTrace();
		}
	
		return res;
	}
	
}
