package pl.com.mat.dbpendulum;

import java.awt.*;
import java.awt.event.*;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.ParseException;
import java.util.ArrayList;

import javax.swing.*;

@SuppressWarnings("serial")
public class ViewManager extends JFrame {
	
	public static final int WIDTH = 806;               //aby panel do rysowania mia³ 800x600
	public static final int HEIGHT = 670;
	
	private DrawPanel panel;
	private JPanel bott;
	private JButton reset;
	
	private JTextField[] txt;
	private JLabel[] lab;
	
	public ViewManager() { 
		super("Math Pendulum");
		setSize(WIDTH, HEIGHT);
		setLocationRelativeTo(null);
		setResizable(false);  
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); 
		
		panel = new DrawPanel();      // panel do wahad³a
		panel.setFocusable(false);
		
		bott = new JPanel();                  // panel dolny zbiorczy
		bott.setLayout(new BoxLayout(bott, BoxLayout.X_AXIS));
		bott.setBorder(BorderFactory.createLineBorder(new Color(255,195,195), 1));
		bott.setFocusable(false);
		
		JPanel[] p = new JPanel[5]; 
		for(int i=0; i<5; i++) {
			p[i] = new JPanel();
			p[i].setFocusable(false);
		}
			
		p[0].setLayout(new BoxLayout(p[0], BoxLayout.Y_AXIS));   // na masy i stick-i
		p[1].setLayout(new BoxLayout(p[1], BoxLayout.X_AXIS));
		p[2].setLayout(new BoxLayout(p[2], BoxLayout.X_AXIS));
		p[3].setLayout(new BoxLayout(p[3], BoxLayout.Y_AXIS));   // na pozostale
		p[4].setLayout(new BoxLayout(p[4], BoxLayout.X_AXIS));
		
		txt = new JTextField[8];
		for(int i=0; i<8; i++) {
			txt[i] = new JTextField();
		}
		txt[0].setText("0,50");
		txt[1].setText("0,50");
		txt[2].setText("1,00");
		txt[3].setText("1,00");
		txt[4].setText("9,81");
		txt[5].setText("0,03");
		txt[6].setText("0,00");
		txt[6].setEditable(false);
		txt[7].setText("0,00");
		txt[7].setEditable(false);
		
		lab = new JLabel[8];
		lab[0] = new JLabel("mass1  ", JLabel.RIGHT);
		lab[1] = new JLabel("mass2  ", JLabel.RIGHT);
		lab[2] = new JLabel("stick1  ", JLabel.RIGHT);
		lab[3] = new JLabel("stick2  ", JLabel.RIGHT);
		lab[4] = new JLabel("gravity  ", JLabel.RIGHT);
		lab[5] = new JLabel("h  ", JLabel.RIGHT);
		lab[6] = new JLabel("fi 1  ", JLabel.RIGHT);
		lab[7] = new JLabel("fi 2  ", JLabel.RIGHT);
		
		for(int i=0; i<4; i++) {
			if(i%2==0) {
				p[1].add(lab[i]);
				p[1].add(txt[i]);
				p[1].add(Box.createRigidArea(new Dimension(50,1)));
			}
			else {
				p[2].add(lab[i]);
				p[2].add(txt[i]);
				p[2].add(Box.createRigidArea(new Dimension(50,1)));
			}
		}
		
		p[0].add(p[1]);
		p[0].add(p[2]);
		
		for(int i=4; i<8; i++) {
			p[4].add(lab[i]);
			p[4].add(txt[i]);
			p[4].add(Box.createRigidArea(new Dimension(5,1)));
		}
		
		p[3].add(Box.createRigidArea(new Dimension(1,10)));
		p[3].add(p[4]);
		p[3].add(Box.createRigidArea(new Dimension(1,10)));
		
		bott.add(Box.createRigidArea(new Dimension(30,1)));
		bott.add(p[0]);
		bott.add(p[3]);
		bott.add(Box.createRigidArea(new Dimension(30,1)));
		
		reset = new JButton("Reset");
		reset.setFocusable(false);
		bott.add(reset);
		bott.add(Box.createRigidArea(new Dimension(50,1)));
		
		getContentPane().add(BorderLayout.CENTER, panel);
		getContentPane().add(BorderLayout.SOUTH, bott);
		setFocusable(true);
		setVisible(true); 
	}
	
	public void addListeners(ActionListener al, MouseListener ml, MouseMotionListener mot, KeyListener kl) {
		panel.addMouseListener(ml);
		panel.addMouseMotionListener(mot);
		
		for(int i=0; i<6; i++)
			txt[i].addKeyListener(kl);
		
		reset.setActionCommand("reset");
		reset.addActionListener(al);
	}
	
	public void refValues() throws ParseException {
		DecimalFormatSymbols symbols = new DecimalFormatSymbols();
		symbols.setDecimalSeparator(',');
		DecimalFormat format = new DecimalFormat("0.#");
		format.setDecimalFormatSymbols(symbols);
		
		for(int i=0; i<6; i++)
			txt[i].setText(String.format("%.2f", format.parse(txt[i].getText()).floatValue()));
	}
	
	public void refArcs(double fi1, double fi2) {
		txt[6].setText(String.format("%.2f", fi1));
		txt[7].setText(String.format("%.2f", fi2));
	}
	
	public ArrayList<Double> getValues() throws ParseException {
		ArrayList<Double> tab = new ArrayList<Double>();
		
		for(int i=0; i<6; i++) 
			tab.add(Double.parseDouble(txt[i].getText().replace(",", ".")));
		
		return tab;
	}
	
	public DrawPanel getDrawPanel() {
		return panel;
	}
}