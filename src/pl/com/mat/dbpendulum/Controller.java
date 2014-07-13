package pl.com.mat.dbpendulum;

import java.awt.event.*;
import java.text.ParseException;

public class Controller implements ActionListener, MouseListener, MouseMotionListener, KeyListener {
	
	private ViewManager win;
	protected static Pendulum pend;   // zmienna przep³ywu danych
	private Refresh rf;
	
	private int chosen;
	
	public Controller(ViewManager win, Refresh rf) {
		this.win = win;
		this.win.addListeners(this, this, this, this);
		this.rf = rf;
		this.rf.start();
	}
	
	public void actionPerformed(ActionEvent e) {       // przycisk reset
			pend.resetPendulum();
	}

	public void keyPressed(KeyEvent e) {               // zatwierdz zmiany parametrow
		if (e.getKeyCode() == KeyEvent.VK_ENTER) {
			try {
				rf.refreshH(pend.setValues(win.getValues()));    // poodœwierzaj parametry w pendulum i refresh
				win.refValues();
			} catch (ParseException e1) {}
		}
	}
	
	public void mousePressed(MouseEvent e) {
		if(e.getSource() == win.getDrawPanel()) {
			rf.setPause(true);
			
			if(pend.getSpreadEli()[0].contains(e.getX(), e.getY())) 
				chosen = 1;
			else if(pend.getSpreadEli()[1].contains(e.getX(), e.getY())) 
				chosen = 2;
		}
	}

	public void mouseDragged(MouseEvent e) {
		if(chosen == 1) 
			pend.refreshArcs(arcBetwLines(0, 1, e.getX()-Pendulum.zX, e.getY()-Pendulum.zY), pend.getVectZ().get(1));
		else if(chosen == 2) 
			pend.refreshArcs(pend.getVectZ().get(0), arcBetwLines(0, 1, e.getX()-pend.getFirstBall()[0], e.getY()-pend.getFirstBall()[1]));
		
		win.refArcs(pend.getArcsDegress().get(0), pend.getArcsDegress().get(1));
	}
	
	public void mouseReleased(MouseEvent e) {
		chosen = 0;
		rf.setPause(false);
	}

	public void keyReleased(KeyEvent arg0) {}

	public void keyTyped(KeyEvent arg0) {}
	
	public void mouseMoved(MouseEvent arg0) {}

	public void mouseClicked(MouseEvent arg0) {}

	public void mouseEntered(MouseEvent arg0) {}

	public void mouseExited(MouseEvent arg0) {}
	
	private double arcBetwLines(double u1, double u2, double w1, double w2) {
		double il = -(u1*w2-u2*w1);
		
		if(il >= 0)
			return Math.acos((u1*w1+u2*w2)/(Math.sqrt(u1*u1+u2*u2)*Math.sqrt(w1*w1+w2*w2)));
		else
			return -Math.acos((u1*w1+u2*w2)/(Math.sqrt(u1*u1+u2*u2)*Math.sqrt(w1*w1+w2*w2)));
	}
}