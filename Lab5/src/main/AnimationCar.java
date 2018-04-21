package main;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

import javax.media.j3d.*;
import javax.swing.Timer;
import javax.vecmath.*;

public class AnimationCar implements ActionListener, KeyListener {
    private TransformGroup car;
    private Transform3D transform3D = new Transform3D();
        
    private float speed = 0.005f;
    private float x = 0;
    private float y = 0;
    private float angle = 0;
    
    private boolean w = false;
    private boolean s = false;
    private boolean a = false;
    private boolean d = false;
    private boolean auto = false;

    public AnimationCar(TransformGroup car) {
        this.car = car;
        this.car.getTransform(this.transform3D);
                
        Timer timer = new Timer(20, this);
        timer.start();
    }
        
    private void Move() {
    	float deltaAngle = 0.005f;
    	
  	  	if (w || auto) {
  	  		x -= speed * Math.sin(angle);
  	  		y += speed * Math.cos(angle);
  	  		deltaAngle = 0.1f;
  	  	}
  	  	
  	  	if (s) {
	  		x += speed * Math.sin(angle);
	  		y -= speed * Math.cos(angle);
	  		deltaAngle = -0.1f;
	  	}
    	
  	  	transform3D.setTranslation(new Vector3f(x, y, 0));
        
        if (a) {
        	angle += deltaAngle;
        	Transform3D rotation = new Transform3D();
    		rotation.rotZ(deltaAngle);
    		transform3D.mul(rotation);
        }
        
        if (d) {
        	angle -= deltaAngle;
        	Transform3D rotation = new Transform3D();
        	rotation.rotZ(-deltaAngle);
        	transform3D.mul(rotation);
        }
        
        car.setTransform(transform3D);
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
    	Move();
    }
    
    @Override
    public void keyPressed(KeyEvent e) {
    	switch (e.getKeyChar()) {
	    	case 'w': w = true; break;
	    	case 's': s = true; break;
	    	case 'a': a = true; break;
	    	case 'd': d = true; break;
	    	case 'q': auto = !auto; break;
	    	case '1': speed = 0.005f; break;
	    	case '2': speed = 0.01f; break;
	    	case '3': speed = 0.02f; break;
		}
    }
    
    @Override
    public void keyTyped(KeyEvent e) {
    	
    }
    
    @Override
    public void keyReleased(KeyEvent e) {
    	switch (e.getKeyChar()) {
	    	case 'w': w = false; break;
	    	case 's': s = false; break;
	    	case 'a': a = false; break;
	    	case 'd': d = false; break;
    	}
    }
}
