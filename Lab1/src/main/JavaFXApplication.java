package main;

import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.scene.shape.Circle;
import javafx.scene.paint.Color;

public class JavaFXApplication extends Application {
	
    public static void main(String[] args) {
    	launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Lab1");
        
        float radius = 90;
        Group root = new Group();
        Scene scene = new Scene(root, 300, 250);        
        scene.setFill(Color.BLACK);

        for (int i = 0; i < 7; i++) {
        	Circle circle = new Circle();
        	circle.setStroke(Color.BLACK);
        	circle.setCenterX(scene.getWidth() / 2);
        	circle.setCenterY(scene.getHeight() / 2);
        	circle.setRadius(radius);
        	
        	switch (i / 2) {
        	case 0:
        		circle.setFill(Color.BLUE);
        		break;
        	case 1:
        		circle.setFill(Color.RED);
        		break;
        	default:
        		circle.setFill(Color.YELLOW);
    		}
        	
            root.getChildren().add(circle);
            radius -= 15;
        }

    	primaryStage.setScene(scene);        	
    	primaryStage.show();
    }
}
