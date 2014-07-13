package pl.com.mat.dbpendulum;

import java.util.ArrayList;
import java.util.List;

public class Refresh extends Thread {
	
	private ViewManager win;    // referencja do widoku  -  by na bie¿¹co odœwierzaæ
	private double h;           // krok h
	private boolean paused;     // czy w¹tek jest zapauzowany
	
	public Refresh(ViewManager win) {
		this.win = win;
		h = Controller.pend.getValues().get(5);
		paused = true;
	}
	
	public void setPause(boolean v) {
		paused = v;
	}
	
	public void refreshH(double vh) {
		h=vh;
	}
	
	public void run() {
		try {
			while(true) {
				if(!paused) {
					RungyKutta();
					win.getDrawPanel().repaint();
					Thread.sleep((long)(1000*h));
				}
				win.getDrawPanel().repaint();
			}
		} catch (InterruptedException e) {}   
	}
	
	private void RungyKutta() {
		ArrayList<Double> an = new ArrayList<Double>();
		ArrayList<Double> bn = new ArrayList<Double>();
		ArrayList<Double> cn = new ArrayList<Double>();
		ArrayList<Double> dn = new ArrayList<Double>();
		@SuppressWarnings("unchecked")
		ArrayList<Double> zn = (ArrayList<Double>)Controller.pend.getVectZ().clone();
		List<Double> v = Controller.pend.getValues();
		
		an.add(zn.get(2));
		an.add(zn.get(3));
		an.add(calc1(zn.get(0),zn.get(1),zn.get(2),zn.get(3),v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5)));
		an.add(calc2(zn.get(0),zn.get(1),zn.get(2),zn.get(3),v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5)));
		
		bn.add(zn.get(2)+0.5*h*an.get(2));
		bn.add(zn.get(3)+0.5*h*an.get(3));
		bn.add(calc1(zn.get(0)+0.5*h*an.get(0),zn.get(1)+0.5*h*an.get(1),zn.get(2)+0.5*h*an.get(2),zn.get(3)+0.5*h*an.get(3),v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5)));
		bn.add(calc2(zn.get(0)+0.5*h*an.get(0),zn.get(1)+0.5*h*an.get(1),zn.get(2)+0.5*h*an.get(2),zn.get(3)+0.5*h*an.get(3),v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5)));
		
		cn.add(zn.get(2)+0.5*h*bn.get(2));
		cn.add(zn.get(3)+0.5*h*bn.get(3));
		cn.add(calc1(zn.get(0)+0.5*h*bn.get(0),zn.get(1)+0.5*h*bn.get(1),zn.get(2)+0.5*h*bn.get(2),zn.get(3)+0.5*h*bn.get(3),v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5)));
		cn.add(calc2(zn.get(0)+0.5*h*bn.get(0),zn.get(1)+0.5*h*bn.get(1),zn.get(2)+0.5*h*bn.get(2),zn.get(3)+0.5*h*bn.get(3),v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5)));
		
		dn.add(zn.get(2)+h*cn.get(2));
		dn.add(zn.get(3)+h*cn.get(3));
		dn.add(calc1(zn.get(0)+h*cn.get(0),zn.get(1)+h*cn.get(1),zn.get(2)+h*cn.get(2),zn.get(3)+h*cn.get(3),v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5)));
		dn.add(calc2(zn.get(0)+h*cn.get(0),zn.get(1)+h*cn.get(1),zn.get(2)+h*cn.get(2),zn.get(3)+h*cn.get(3),v.get(0),v.get(1),v.get(2),v.get(3),v.get(4),v.get(5)));
		
		Controller.pend.refreshVectZ(zn.get(0)+(h/6)*(an.get(0)+2*bn.get(0)+2*cn.get(0)+dn.get(0)), 
				zn.get(1)+(h/6)*(an.get(1)+2*bn.get(1)+2*cn.get(1)+dn.get(1)), 
				zn.get(2)+(h/6)*(an.get(2)+2*bn.get(2)+2*cn.get(2)+dn.get(2)), 
				zn.get(3)+(h/6)*(an.get(3)+2*bn.get(3)+2*cn.get(3)+dn.get(3)));
		
		win.refArcs(Controller.pend.getArcsDegress().get(0), Controller.pend.getArcsDegress().get(1));   // odœwierzenie widoku katów
	}
	
	private double calc1(double fi1, double fi2, double v1, double v2, double m1 , double m2, 
			double l1, double l2, double g, double h) {
		return (-g*(2*m1+m2)*Math.sin(fi1)-m2*g*Math.sin(fi1-2*fi2)-2*Math.sin(fi1-fi2)*m2*(v2*v2*l2+v1*v1*l1*Math.cos(fi1-fi2)))/(l2*(2*m1+m2-m2*Math.cos(2*fi1-2*fi2)));
	}
	
	private double calc2(double fi1, double fi2, double v1, double v2, double m1, double m2, 
			double l1, double l2, double g, double h) {
		return (2*Math.sin(fi1-fi2)*(v1*v1*l1*(m1+m2)+g*(m1+m2)*Math.cos(fi1)+v2*v2*l2*m2*Math.cos(fi1-fi2)))/(l2*(2*m1+m2-m2*Math.cos(2*fi1-2*fi2)));
	}
	
	// wzory wymno¿one
	/*private double calc1(double fi1, double fi2, double v1, double v2, double m1 , double m2, 
			double l1, double l2, double g, double h) {
		return (m2*l1*v1*v1*Math.sin(fi2-fi1)*Math.cos(fi2-fi1)+m2*g*Math.sin(fi2)*Math.cos(fi2-fi1)+m2*l2*v2*v2*Math.sin(fi2-fi1)-(m1+m2)*g*Math.sin(fi1))/((m1+m2)*l1-m2*l1*Math.cos(fi2-fi1)*Math.cos(fi2-fi1));
	}
	
	private double calc2(double fi1, double fi2, double v1, double v2, double m1, double m2, 
			double l1, double l2, double g, double h) {
		return (-m2*l2*v2*v2*Math.sin(fi2-fi1)*Math.cos(fi2-fi1)+(m1+m2)*(g*Math.sin(fi1)*Math.cos(fi2-fi1)-l1*v1*v1*Math.sin(fi2-fi1)-g*Math.sin(fi2)))/((m1+m2)*l2-m2*l2*Math.cos(fi2-fi1)*Math.cos(fi2-fi1));
	}*/
}