package project5;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

public class MyMazeMain extends JFrame {

	private static final long serialVersionUID = 1L;
	public static int MAZE_WIDTH;
	public static int MAZE_HEIGHT;

	public MyMazeMain(int cols, int rows) {
		MyMazeMain.MAZE_WIDTH = cols * cols;
		MyMazeMain.MAZE_HEIGHT = rows * rows;
		getContentPane().add(new MyMazePanel(cols, rows));
		this.setBackground(MyMazePanel.bgColor);
		this.setSize(MAZE_WIDTH, MAZE_HEIGHT);
		this.setLocationRelativeTo(null);
		this.setVisible(true);
	}

	public static void main(String[] args) {
		int rows = 20;
		int cols = 20;
		MyMazeMain app = new MyMazeMain(rows, cols);
		System.out.println("App started...");
		app.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.out.println("App Closed......");
				System.exit(0);
			}
		});
	}
}