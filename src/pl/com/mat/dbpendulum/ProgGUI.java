package pl.com.mat.dbpendulum;

public class ProgGUI {

	public static void main(String[] args) {
		Controller.pend = new Pendulum();
		try {
			ViewManager win=new ViewManager();
			Refresh rf = new Refresh(win);
			new Controller(win, rf);
		}
		catch (Exception e) {
			System.out.println("ERROR");
		}
	}
}
