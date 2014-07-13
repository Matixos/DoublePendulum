package pl.com.mat.dbpendulum;

import java.awt.*;
import java.awt.geom.*;

import javax.swing.*;

@SuppressWarnings("serial")
public class DrawPanel extends JPanel {
	
	public DrawPanel() {
		super();
		setBackground(Color.WHITE);
	}
	
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		Graphics2D g2d = (Graphics2D)g;
		g2d.setColor(Color.BLACK);
		
		for(Shape i: Controller.pend.getPendulum()) {
			if(i instanceof Ellipse2D) {
				g2d.setColor(Color.BLUE);
				g2d.fill(i); 
			}
			else 
				g2d.draw(i);
		}
	}
}
