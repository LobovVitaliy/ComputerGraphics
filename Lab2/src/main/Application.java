package main;

import java.awt.*;
import java.awt.MultipleGradientPaint.CycleMethod;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.geom.Point2D;
import java.awt.geom.GeneralPath;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.Timer;

@SuppressWarnings("serial")
public class Application extends JPanel implements ActionListener {
	
	private static int maxWidth;
	private static int maxHeight;
	
	private Timer timer;
	private double angle = 0;
	private double transparence = 1;
	private double delta = 0.005;
	
	public Application() {
		timer = new Timer(10, this);
		timer.start();
	}
	
	private void paintCircles(Graphics2D g2d, int width, int height) {
		int radius = 180;
		
		for (int i = 0; i < 7; i++) {
			int x = (width - radius) / 2;
			int y = (height - radius) / 2;
			
			Point2D center = new Point2D.Double(width / 2, height / 2);
			Point2D focus = new Point2D.Double(45, 25);
			float[] dist = new float[] { 0, 0.5f };
			CycleMethod method = CycleMethod.NO_CYCLE;
			Color[] colors;
			
			switch (i / 2) {
	    	case 0:
	    		//g2d.setColor(Color.BLUE);
	    		colors = new Color[] { Color.BLUE, Color.RED };
	    		g2d.setPaint(new RadialGradientPaint(center, width, focus, dist, colors, method));
	    		break;
	    	case 1:
	    		//g2d.setColor(Color.RED);
	    		colors = new Color[] { Color.RED, Color.YELLOW };
	    		g2d.setPaint(new RadialGradientPaint(center, width, focus, dist, colors, method));
	    		break;
	    	default:
	    		g2d.setPaint(Color.ORANGE);
			}

			g2d.fillOval(x, y, radius, radius);
			g2d.setColor(Color.BLACK);
			g2d.drawOval(x, y, radius, radius);
			
	        radius -= 30;
	    }
		
		g2d.drawLine(width / 2, height / 2, width / 2, height / 2);
	}
	
	private void paintFigure(Graphics2D g2d, int width, int height) {
		double points[][] = {
			{ 0, 0 },
			{ width, height / 2 },
			{ width, height },
			{ 0, height / 2 },
			{ width, 0 },
			{ width, height / 2 },
			{ 0, height },
		};
		
		GeneralPath star = new GeneralPath(); 
        star.moveTo(points[0][0], points[0][1]);
	    for (int i = 1; i < points.length; i++) {
	    	star.lineTo(points[i][0], points[i][1]);
	    }
        star.closePath();
        
        g2d.translate(-1, 0);
        g2d.setColor(Color.ORANGE);
        g2d.draw(star);
	}
	
	private void paintFrame(Graphics2D g2d, int width, int height) {
		setColorAndStroke(g2d);
        g2d.translate(5, 5);
        g2d.drawRect(width + 1, 0, width - 10, height - 10);
	}
	
	private void paintAnimation(Graphics2D g2d, int width, int height) {
		g2d.translate(width + 1 + width / 2, height / 2);
        g2d.rotate(angle, 0, 0);
        g2d.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_OVER, (float)transparence));
        
        setColorAndStroke(g2d);
        g2d.drawLine(0, 0, 0, 150);
        
        g2d.translate(-25 / 2, 0);
        g2d.setColor(Color.RED);
        g2d.fillOval(0, 150, 25, 25);
	}
	
	private void setColorAndStroke(Graphics2D g2d) {
		g2d.setColor(Color.ORANGE);
		g2d.setStroke(new BasicStroke(5, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL));
	}
	
	public void paint(Graphics g) {
		super.paint(g); // ?
		
		Graphics2D g2d = (Graphics2D) g;
		
		g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
        g2d.setRenderingHint(RenderingHints.KEY_RENDERING, RenderingHints.VALUE_RENDER_QUALITY);
        
		g2d.setBackground(Color.BLACK);
		g2d.clearRect(0, 0, maxWidth, maxHeight);

		int width = maxWidth / 2;
		int height = maxHeight;

		// Circles
		paintCircles(g2d, width, height);
		
		// Figure
		paintFigure(g2d, width, height);
        
        // Frame
		paintFrame(g2d, width, height);
        
        // Animation
		paintAnimation(g2d, width, height);
	}
	
	@Override
	public void actionPerformed(ActionEvent event) {
		if (transparence < 0.005 || transparence > 0.995) {
			delta = -delta;
		}
		
		transparence += delta;
		angle += 0.01;
		
		repaint();
	}
	
	public static void main(String[] args) {
		JFrame frame = new JFrame("Lab2");
		frame.add(new Application());
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.setSize(1000, 500);
		frame.setLocationRelativeTo(null);
		frame.setResizable(false);
		frame.setVisible(true);
		
		Dimension size = frame.getSize();
		Insets insets = frame.getInsets();
		maxWidth =  size.width - insets.left - insets.right;
		maxHeight =  size.height - insets.top - insets.bottom;
	 }
}
