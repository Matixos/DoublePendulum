package pl.com.mat.dbpendulum;

import java.awt.Shape;
import java.awt.geom.*;
import java.util.ArrayList;
import java.util.List;

public class Pendulum{
	public static int zX = 400;       // punkt zaczepienia
	public static int zY = 300;
	private int multL = 85;           // mnoznik dla dlugosci nici
	private double[] firBall;         // koordynaty pierwszej kulki
	
	private ArrayList<Double> vectZ; // wektor stanu
	private List<Double> val;        // parametry
	private Shape[] pend;            // kszta³t do rysowania
	
	public Pendulum() {
		pend = new Shape[5];
		val = new ArrayList<Double>();
		val.add(0.5);	val.add(0.5);		// pocz¹tkowe wartoœci
		val.add(1.0); 	val.add(1.0);       
		val.add(9.81);	val.add(0.03);   
		
		firBall = new double[2];
		vectZ = new ArrayList<Double>();
		resetPendulum();
	}
	
	public void resetPendulum() {
		refreshVectZ(0, 0, 0, 0);
	}
	
	public double setValues(ArrayList<Double> v) {
		val = v;
		return v.get(5);
	}
	
	private void refreshCoordinates(double fi1, double fi2) {         // ju¿ po zmianie koordynatow
		double x1 = val.get(2)*multL*Math.sin(fi1)+zX;
		double y1 = val.get(2)*multL*Math.cos(fi1)+zY;
		double x2 = x1+val.get(3)*multL*Math.sin(fi2);
		double y2 = y1+val.get(3)*multL*Math.cos(fi2);
		
		firBall[0] = x1;
		firBall[1] = y1;
		
		pend[0] = new Line2D.Double(zX, zY, x1, y1);
		pend[1] = new Line2D.Double(x1, y1, x2, y2);
		pend[2] = new Ellipse2D.Double(zX-2, zY-2, 4, 4);
		pend[3] = new Ellipse2D.Double(x1-10, y1-10, 20, 20);
		pend[4] = new Ellipse2D.Double(x2-10, y2-10, 20, 20);
	}
	
	public void refreshArcs(double fi1, double fi2) {
		refreshVectZ(fi1, fi2, vectZ.get(2), vectZ.get(2));
	}
	
	public void refreshVectZ(double fi1, double fi2, double v1, double v2) {
		vectZ.clear();
		vectZ.add(fi1);  vectZ.add(fi2);    
		vectZ.add(v1);   vectZ.add(v2);
		
		refreshCoordinates(vectZ.get(0), vectZ.get(1));
	}
	
	public double[] getFirstBall() {
		return firBall;
	}
	
	public List<Double> getValues() {
		return val;
	}
	
	public ArrayList<Double> getVectZ() {
		return vectZ;
	}
	
	public ArrayList<Double> getArcsDegress() {
		ArrayList<Double> temp = new ArrayList<Double>();
		temp.add(validateArc(vectZ.get(0)));
		temp.add(validateArc(vectZ.get(1)));
		
		return temp;
	}
	
	private double validateArc(double arc) {          // zwraca odpowiednia wielkosc k¹ta w stopniach
		if(((int)(Math.toDegrees(arc)/180))%2==0)
			return Math.toDegrees(arc)%180;
		else
			if(Math.toDegrees(arc)>0)
				return -180+(Math.toDegrees(arc)%180);
			else
				return 180+(Math.toDegrees(arc)%180);
	}
	
	public Shape[] getSpreadEli() {       // by mo¿na by³o ³atwieæ z³apaæ myszk¹ kulki
		Shape[] temp = new Shape[2];
		temp[0] = new Ellipse2D.Double(pend[3].getBounds().getCenterX()-(val.get(3)*multL/2), pend[3].getBounds().getCenterY()-(val.get(3)*multL/2), val.get(3)*multL, val.get(3)*multL);
		temp[1] = new Ellipse2D.Double(pend[4].getBounds().getCenterX()-(val.get(3)*multL/2), pend[4].getBounds().getCenterY()-(val.get(3)*multL/2), val.get(3)*multL, val.get(3)*multL);
		
		return temp;
	}
	
	public Shape[] getPendulum() {
		return pend;
	}
}