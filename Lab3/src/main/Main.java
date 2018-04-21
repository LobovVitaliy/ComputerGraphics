package main;

import java.io.IOException;
import java.io.BufferedInputStream;
import java.io.FileInputStream;

import javafx.animation.PathTransition;
import javafx.animation.RotateTransition;
import javafx.animation.ScaleTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.stage.Stage;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.shape.LineTo;
import javafx.scene.shape.MoveTo;
import javafx.scene.shape.Path;
import javafx.scene.shape.PathElement;
import javafx.util.Duration;

public class Main extends Application {
	
	private static int width;
	private static int height;
	private static int half;
	
	@Override
	public void start(Stage stage) throws IOException {
		int[][] black = readAndGetTrajectory();
	    int numberOfPixels = black.length;

	    System.out.println("number of black color pixels = " + numberOfPixels);
		
		Path path = new Path();
		ObservableList<PathElement> elements = path.getElements();
		
		for(int i = 0; i < numberOfPixels - 1; i++) {
			elements.addAll(
				new MoveTo(black[i][0], black[i][1]),
				new LineTo(black[i + 1][0], black[i + 1][1])
			);
		}
		
		Group root = new Group();
		Scene scene = new Scene(root, width, height);
		
		Group fish = Fish.create(width / 2, height / 2, 3);
		fish.setScaleX(0.5);
		fish.setScaleY(0.5);
		
		ScaleTransition st = new ScaleTransition(Duration.millis(2000), fish);
		st.setByX(-0.2);
		st.setByY(-0.2);
		st.setCycleCount(2);
		st.setAutoReverse(true);
		st.play();
		
		RotateTransition rt = new RotateTransition(Duration.millis(2000), fish);
		rt.setByAngle(45);
	    rt.setCycleCount(2);
	    rt.setAutoReverse(true);
	    rt.play();
	    
	    PathTransition pt = new PathTransition();
		pt.setDuration(Duration.millis(5000));
		pt.setPath(path);
		pt.setNode(fish);
		pt.play();
	    
		root.getChildren().add(fish);
		
		stage.setScene(scene);
		stage.show();
	}
	
	
	private int[][] readAndGetTrajectory() throws IOException {
		char[][] map = new char[width][height];
		int numberOfPixels = 0;
		int let, let1, let2;
		
		BufferedInputStream reader = new BufferedInputStream(new FileInputStream("pixels.txt"));
		
		for(int i = 0; i < height; i++) {
			for(int j = 0; j < half; j++) {
				let = reader.read();
				let1 = let;
				let2 = let;
				let1 = let1 & 0xf0;
				let1 = let1 >> 4;
	          	let2 = let2 & 0x0f;
	          	
	          	if (j * 2 < width) {
	          		if (returnPixelColor(let1) == "BLACK") {
	          			map[j * 2][height - 1 - i] = '1';
	          			numberOfPixels++;
	          		} else {
	          			map[j * 2][height - 1 - i] = '0';
	          		}
	          	}
	          	if (j * 2 + 1 < width) {   
	          		if (returnPixelColor(let2) == "BLACK") {
	          			map[j * 2 + 1][height - 1 - i] = '1';
	                    numberOfPixels++;
	                } else {                    
	                    map[j * 2 + 1][height - 1 - i] = '0'; 
	                }
	          	}
			}
		}
		
		reader.close();
		
		int[][] black = new int[numberOfPixels][2];	
		int lich = 0;
		
		for(int i = 0; i < height; i++) { 
			for(int j = 0; j < width; j++) {
				if (map[j][i] == '1') {
					black[lich][0] = j;
					black[lich][1] = i;
					lich++;
			   }
		   }
		}
		
		return black;
	}
	
	
	// метод для співставлення кольорів 16-бітного зображення
	private String returnPixelColor(int color) {
		switch(color) {
    		case 0:  return "BLACK";
	    	case 1:  return "LIGHTCORAL";
	        case 2:  return "GREEN";
	        case 3:  return "BROWN";
	        case 4:  return "BLUE";
	        case 5:  return "MAGENTA";
	        case 6:  return "CYAN";
	        case 7:  return "LIGHTGRAY";
	        case 8:  return "DARKGRAY";
	        case 9:  return "RED";
	      	case 10: return "LIGHTGREEN";
	      	case 11: return "YELLOW";
	      	case 12: return "LIGHTBLUE";
	      	case 13: return "LIGHTPINK";
	      	case 14: return "LIGHTCYAN";
	      	case 15: return "WHITE";
	      	default: return "BLACK";
	    }
	}
	
	
	public static void main(String[] args) throws IOException {
		HeaderBitmapImage image = ReadingImageFromFile.loadBitmapImage("Images/trajectory.bmp");
		
		width = (int) image.getWidth();
		height = (int) image.getHeight();
		half = (int) image.getHalfOfWidth();
		
		launch(args);
	}
}
