package main;

import javafx.scene.Group;
import javafx.scene.paint.Color;
import javafx.scene.shape.Arc;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Ellipse;
import javafx.scene.shape.Path;
import javafx.scene.shape.QuadCurve;
import javafx.scene.shape.Rectangle;

public class Fish {
	
	private static Color ORANGE = Color.rgb(234, 195, 14);
	private static Color STROKE_COLOR = Color.rgb(108, 91, 42);
	private static Color STRIP_COLOR = Color.rgb(243, 222, 85);
	private static Color FIN_COLOR = Color.rgb(225, 102, 48);
	private static Color EYE_COLOR = Color.rgb(226, 233, 205);
	private static Color PURPLE = Color.rgb(124, 115, 181);
	private static Color GREEN = Color.rgb(67, 183, 117);
	private static Color PINK = Color.rgb(249, 110, 121);
	
	public static Group create(double x, double y, int scale) {
		Group fish = new Group();
		fish.getChildren().addAll(
			createUpperFin(x, y, scale),
			createBody(x, y, scale),
			createEye(x, y, scale),
			createTail(x, y, scale),
			createFishScales(x, y, scale),
			createLowerFin(x, y, scale),
			createStrip(x, y, scale)
		);
		return fish;
	}
	
	private static Path createBody(double x, double y, int scale) {
		Ellipse body = new Ellipse(x, y, 45 * scale, 40 * scale);
		
		Ellipse mouth1 = new Ellipse(x - 125, y, 9 * scale, 6 * scale);
		Ellipse mouth2 = new Ellipse(x - 123, y + 20, 9 * scale, 6 * scale);
		
		Path fish = (Path) Path.union(body, mouth1);
		fish = (Path) Path.union(fish, mouth2);
		fish.setFill(ORANGE);
		fish.setStroke(STROKE_COLOR);
		
		return fish;
	}
	
	private static Group createEye(double x, double y, int scale) {
		Group eye = new Group();
		
		Ellipse eye1 = new Ellipse(x - 85, y - 30, 6 * scale, 7 * scale);
		eye1.setFill(EYE_COLOR);
		eye1.setStroke(STROKE_COLOR);
		
		Ellipse eye2 = new Ellipse(x - 85, y - 22, 5.2 * scale, 4.2 * scale);
		
		Arc eyebrow = new Arc(x - 85, y - 30, 10 * scale, 10 * scale, 65, 50);
		eyebrow.setStrokeWidth(2);
		eyebrow.setFill(Color.TRANSPARENT);
		eyebrow.setStroke(STROKE_COLOR);
		
		eye.getChildren().addAll(eye1, eye2, eyebrow);
		return eye;
	}
	
	private static Arc createStrip(double x, double y, int scale) {
		Arc strip = new Arc(x - 275, y, 80 * scale, 75 * scale, -28, 56);
		strip.setStrokeWidth(1.5);
		strip.getStrokeDashArray().addAll(3.0, 7.0);
		strip.setFill(Color.TRANSPARENT);
		strip.setStroke(STRIP_COLOR);
		return strip;
	}
	
	private static Path createUpperFin(double x, double y, int scale) {
		QuadCurve main = new QuadCurve(x - 55, y - 110, x - 45, -10, x + 95, y - 85);
		Ellipse minus = new Ellipse(x - 75, y - 155, 16 * scale, 18 * scale);
		
		Path fin = (Path) Path.subtract(main, minus);
		fin.setFill(FIN_COLOR);
		fin.setStroke(STROKE_COLOR);
		
		return fin;
	}

	private static Path createLowerFin(double x, double y, int scale) {
		Arc fin1 = new Arc(x - 0, y + 128, 6 * scale, 16 * scale, 90, 90);
		fin1.setType(ArcType.ROUND);
		
		Arc fin2 = new Arc(x - 0, y + 125, 6 * scale, 8.4 * scale, 180, 90);
		fin2.setType(ArcType.ROUND);
		
		QuadCurve fin3 = new QuadCurve(x - 0, y + 80, x + 25, y + 145, x - 0, y + 150);
		
		Path fin = (Path) Path.union(fin1, fin2);
		fin = (Path) Path.union(fin, fin3);
		fin.setRotate(-30);
		fin.setScaleX(1.2);
		fin.setScaleY(1.2);
		fin.setFill(FIN_COLOR);
		fin.setStroke(STROKE_COLOR);
		
		return fin;
	}
	
	private static Path createTail(double x, double y, int scale) {
		Arc tail1 = new Arc(x + 100, y - 40, 25 * scale, 25 * scale, -90, 180);
		Arc tail2 = new Arc(x + 100, y + 40, 25 * scale, 25 * scale, -90, 180);

		Ellipse tail3 = new Ellipse(x + 115, y - 65, 15 * scale, 14 * scale);
		Ellipse tail4 = new Ellipse(x + 115, y + 65, 15 * scale, 14 * scale);
		
		Rectangle rec = new Rectangle(x + 80, y - 250 / 2, 50, 250);
		
		Rectangle minus1 = new Rectangle(x + 130, y - 115, 17 * scale, 13 * scale);
		Rectangle minus2 = new Rectangle(x + 130, y + 115 - 13 * scale, 17 * scale, 13 * scale);
		
		Path tail = (Path) Path.union(tail1, tail2);
		tail = (Path) Path.subtract(tail, tail3);
		tail = (Path) Path.subtract(tail, tail4);
		tail = (Path) Path.subtract(tail, rec);
		tail = (Path) Path.subtract(tail, minus1);
		tail = (Path) Path.subtract(tail, minus2);
		
		tail = (Path) Path.union(tail, new Circle(x + 125, y - 17.5, 4 * scale));
		tail = (Path) Path.union(tail, new Circle(x + 120, y, 4 * scale));
		tail = (Path) Path.union(tail, new Circle(x + 125, y + 17.5, 4 * scale));
		
		tail = (Path) Path.union(tail, new Circle(x + 162.2, y - 76, 3.5));
		tail = (Path) Path.union(tail, new Circle(x + 162.2, y + 76, 3.5));
		
		tail.setScaleX(1.1);
		tail.setScaleY(1.1);
		
		tail.setFill(FIN_COLOR);
		tail.setStroke(STROKE_COLOR);
		
		return tail;
	}

	private static Group createFishScales(double x, double y, int scale) {
		Group fishScales = new Group();
		
		double radius = 8;
		
		// 1
		Circle fishScale11 = new Circle(x - 30, y - 80, radius * scale);
		Circle fishScaleCenter11 = new Circle(x - 30 + 3, y - 80 - 1, 2 * scale);
		fishScaleCenter11.setFill(Color.WHITE);
		
		Circle fishScale12 = new Circle(x - 15, y - 28, radius * scale);
		Circle fishScaleCenter12 = new Circle(x - 15 + 2, y - 28, 2 * scale);
		fishScaleCenter12.setFill(Color.WHITE);
		
		Circle fishScale13  = new Circle(x - 15, y + 25, radius * scale);
		Circle fishScaleCenter13  = new Circle(x - 15 + 2, y + 25 + 1, 2 * scale);
		fishScaleCenter13.setFill(Color.WHITE);
		
		Arc minus = new Arc(x - 265, y, 80 * scale, 75 * scale, -29, 58);
		
		Path path1 = (Path) Path.subtract(fishScale11, minus);
		path1.setFill(PURPLE);
		Path path2 = (Path) Path.subtract(fishScale12, minus);
		path2.setFill(PURPLE);
		Path path3 = (Path) Path.subtract(fishScale13, minus);
		path3.setFill(PURPLE);
		
		Group g1 = new Group();
		g1.getChildren().addAll(
			path1, fishScaleCenter11,
			path2, fishScaleCenter12,
			path3, fishScaleCenter13
		);
		
		//2
		Circle fishScale21  = new Circle(x + 10, y - 64, radius * scale);
		fishScale21.setFill(GREEN);
		Circle fishScaleCenter21  = new Circle(x + 10, y - 64, 2 * scale);
		fishScaleCenter21.setFill(Color.WHITE);
		
		Circle fishScale22  = new Circle(x + 19, y - 1, radius * scale);
		fishScale22.setFill(GREEN);
		Circle fishScaleCenter22  = new Circle(x + 19, y - 1, 2 * scale);
		fishScaleCenter22.setFill(Color.WHITE);

		Circle fishScale23  = new Circle(x + 19, y + 55, radius * scale);
		fishScale23.setFill(GREEN);
		Circle fishScaleCenter23  = new Circle(x + 19, y + 55, 2 * scale);
		fishScaleCenter23.setFill(Color.WHITE);
		
		Circle minus21  = new Circle(x - 30, y - 80, 10 * scale);
		minus21.setFill(ORANGE);
		Circle minus22  = new Circle(x - 15, y - 28, 10 * scale);
		minus22.setFill(ORANGE);
		Circle minus23  = new Circle(x - 15, y + 25, 10 * scale);
		minus23.setFill(ORANGE);
		Circle minus24  = new Circle(x - 22, y + 76, 10 * scale);
		minus24.setFill(ORANGE);
		
		Group g2 = new Group();
		g2.getChildren().addAll(
			fishScale21, fishScaleCenter21,
			fishScale22, fishScaleCenter22,
			fishScale23, fishScaleCenter23,
			minus21, minus22, minus23, minus24
		);
		
		//3
		Circle fishScale31  = new Circle(x + 49, y - 38, radius * scale);
		fishScale31.setFill(PINK);
		Circle fishScaleCenter31  = new Circle(x + 49, y - 38, 2 * scale);
		fishScaleCenter31.setFill(Color.WHITE);
		
		Circle fishScale32  = new Circle(x + 55, y + 27, radius * scale);
		fishScale32.setFill(PINK);
		Circle fishScaleCenter32  = new Circle(x + 55, y + 27, 2 * scale);
		fishScaleCenter32.setFill(Color.WHITE);
		
		Circle minus31 = new Circle(x + 10, y - 64, 11 * scale);
		minus31.setFill(ORANGE);
		Circle minus32  = new Circle(x + 19, y - 1, 11 * scale);
		minus32.setFill(ORANGE);
		Circle minus33  = new Circle(x + 19, y + 55, 11 * scale);
		minus33.setFill(ORANGE);
		
		Group g3 = new Group();
		g3.getChildren().addAll(
			fishScale31, fishScaleCenter31,
			fishScale32, fishScaleCenter32,
			minus31, minus32, minus33
		);

		fishScales.getChildren().addAll(g3, g2, g1);
		return fishScales;
	}
}
