package main;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;

public class ReadingHeaderFromBitmapImage {
		
	public static HeaderBitmapImage reading(BufferedInputStream reader) throws IOException {
		HeaderBitmapImage hbi = new HeaderBitmapImage();
		
		int ch, i = 0;
		long offset = 0;
		
		// ���� �� ����� �����
		while ((ch = reader.read()) != -1) {
			i++;
			
			if (i < 54) {
				long temp = reader.read();
				hbi.setType((short) ((temp * 0x100) + ch));
				
				long size = readLong(reader);
				hbi.setSize(size);
				
				short reserveField1 = readShort(reader);
				hbi.setReserveField1(reserveField1);
				
				short reserveField2 = readShort(reader);
				hbi.setReserveField2(reserveField2);
				
				offset = readLong(reader);
				hbi.setOffset(offset);
				
				long sizeOfHeader = readLong(reader);
				hbi.setSizeOfHeader(sizeOfHeader);
				
				long width = readLong(reader);
				hbi.setWidth(width);
				
				long height = readLong(reader);
				hbi.setHeight(height);
				
				long half = width;
				if ((half % 2) != 0) half++;
				half /= 2;
				if ((half % 4) != 0) half = (half / 4) * 4 + 4;
				
				hbi.setHalfOfWidth(half);
				
				short numberOfColorPlanes = readShort(reader);
				hbi.setNumberOfColorPlanes(numberOfColorPlanes);
				
				short bitsCount = readShort(reader);
				hbi.setBitsCount(bitsCount);
				
				long compression = readLong(reader);
				hbi.setCompression(compression);
				
				long sizeOfCompImage = readLong(reader);
				hbi.setSizeOfCompImage(sizeOfCompImage);
				
				long horizontalResolution = readLong(reader);
				hbi.setHorizontalResolution(horizontalResolution);
				
				long verticalResolution = readLong(reader);
				hbi.setVerticalResolution(verticalResolution);
				
				long numbOfUsedColors = readLong(reader);
				hbi.setNumbOfUsedColors(numbOfUsedColors);
				
			    long numbOfImportantColors = readLong(reader);
			    hbi.setNumbOfImportantColors(numbOfImportantColors);
			    
			    i = 54;
			}
			
			// �������� ���� � ������, �� ����������� �����
			if (i == offset) {
				reader.mark(1);
				break;
			}
		}
		
		// ��������� �� �� ���� ����� ����� ���������� �����
		reader.reset();

		// �������� � ������� ���� ������� ����������, � ��� ����������� ������ �����
		BufferedOutputStream writer = new BufferedOutputStream(new FileOutputStream("pixels.txt"));
		while ((ch = reader.read()) != -1) {
			writer.write(ch);
		}
		writer.close();
				
		return hbi;
	}

	// ����� ��� ���������� ������� ���� ������� 2 ����� � ������ ������ little-endian
	// �� �������� � ��������� ������� ��������
	private static short readShort(BufferedInputStream reader) throws IOException {
		long temp = 0;
		short value = 0;
		
		// ���� �� 1 �� 8 � ������ j*4 - �������� ������� �������� ����� = 2
		for(long j = 0x1; j <= 0x1000; j *= 0x100) {
			temp = reader.read(); 
			value += temp * j; // ������ �� ��������� ����� �������� ������ ������� ���������� � 10-�� ������ ��������  
		}
		return value;
	}
	
	// ����� ��� ���������� ������� ���� ������� 4 ����� � ������ ������ little-endian
	// �� �������� � ��������� ������� ��������
	private static long readLong(BufferedInputStream reader) throws IOException {
		long temp = 0;
		long value = 0;
		
		// ���� �� 1 �� 64 � ������ j*4 - �������� ������� �������� ����� = 4
		for(long j = 0x1; j <= 0x1000000; j *= 0x100) {
			temp = reader.read();
		   	value += temp * j; // ������ �� ��������� ����� �������� ������ ������� ���������� � 10-�� ������ ��������
		}
		return value;
	}
}
