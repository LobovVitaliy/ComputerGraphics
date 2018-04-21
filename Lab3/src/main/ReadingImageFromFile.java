package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadingImageFromFile {
		
	public static HeaderBitmapImage loadBitmapImage(String filename) throws IOException {
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream(filename));
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("primer-bmp.txt"));
		
		int ch;
		while ((ch = reader.read()) != -1) {
			writer.write(ch);
		}
		
		writer.close();
		reader.close();
		
		BufferedInputStream reader1 = new BufferedInputStream(new FileInputStream("primer-bmp.txt"));
		HeaderBitmapImage hbi = ReadingHeaderFromBitmapImage.reading(reader1);
		reader1.close();
		
		return hbi;
	}
}
