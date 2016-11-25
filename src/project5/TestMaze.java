package project5;

import java.awt.Color;
import java.awt.Container;
import java.awt.Graphics;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Random;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class TestMaze extends JFrame {
	/**
	 * The constructor used to create JFrame. Has on it a MazePanel where the
	 * maze where the maze is acutally drawn.
	 * 
	 * @param width
	 *            - how wide in pixels frame is
	 * @param height-
	 *            how high in pixels frame is
	 * @param cols
	 *            - how many cells wide the maze is
	 * @param rows
	 *            - how many cells high the maze is
	 */
	public TestMaze(int width, int height, int cols, int rows) {
		super("Maze Drawer");

		Container c = getContentPane();

		c.add(new MazePanel(cols, rows));

		setBackground(Color.white);
		setSize(width, height);
	}

	/**
	 * Set-up frame and window closing listener.
	 * 
	 * @param args
	 *            - command line arguments used to determine size of frame and
	 *            maze
	 */
	public static void main(String[] args) {
		TestMaze app = new TestMaze(300, 300, 20, 20);

		app.addWindowListener(new WindowAdapter() // anonymous inner class
		{
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});

		app.show();
	}

} // end cs146sec6hw5file1.java

class MazePanel extends JPanel {
	/*
	 * Constructor for MazePanel. Sets up both its size in pixels and the number
	 * of cells wide and high it is.
	 */
	MazePanel(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;

		cellX = 300 / (cols + 2);
		cellY = 300 / (rows + 2);
	}

	/*
	 * Method called when maze needs to be drawn to a graphics context g -
	 * graphics constext on which maze is drawn.
	 */
	public void paintComponent(Graphics g) {
		g.setColor(Color.black);
		drawGrid(g);
		getDisjointSets(g);
	}

	/*
	 * returns the preferred size of the MazePanel so that it will be drawn
	 * correctly of frame.
	 */

	/*
	 * This method draws the complete grid with four walls around each cell.
	 * 
	 * g - graphics context we are drawing to.
	 */
	public void drawGrid(Graphics g) {

		for (int y = cellY, i = 0; i <= rows; i++, y += cellY) {
			for (int x = cellX, j = 0; j <= cols; j++, x += cellX) {
				// g.fillOval(x - 2, y - 2, 4, 4);
				if (j < cols)
					g.drawLine(x, y, x + cellX, y);
				if (i < rows)
					g.drawLine(x, y, x, y + cellY);
			}
		}
	}

	/*
	 * This method uses randomness and the disjoint Set ADT to delete edges from
	 * the full grid that has already previously been drawn to create a maze. To
	 * ensure that our maze doesn't change under repaints our random number
	 * generator always starts with the same seed.
	 */
	void getDisjointSets(Graphics g) {
		int gridSize = cols * rows;
		int numComponents = gridSize;
		DisjointSets connectedCells = new DisjointSets(gridSize);
		Random random = new Random(randomSeed);

		while (numComponents > 1) {
			int cell = random.nextInt(gridSize);
			int direction = random.nextInt(2);
			int neighbour = cell;
			int tmp;

			if (direction > 0) {
				if ((tmp = cell + cols) < gridSize)
					neighbour = tmp;
			} else if (cell % cols < cols - 1)
				neighbour++;

			int nRoot = connectedCells.find(neighbour);
			int cRoot = connectedCells.find(cell);
			if (nRoot != cRoot) {
				connectedCells.union(nRoot, cRoot);
				removeLine(g, cell, neighbour);
				numComponents--;
			}
		}

	}

	/*
	 * Used to delete the edge between cell and neighbour in the maze image that
	 * has already been drawn to g.
	 */
	void removeLine(Graphics g, int cell, int neighbour) {
		int cRow = cell / cols;
		int cColumn = cell % cols;
		int nRow = neighbour / cols;
		int nColumn = neighbour % cols;

		if (cRow == nRow) {
			cRow++;
			nRow += 2;
			cColumn += 2;
			nColumn = cColumn;
		} else {
			cColumn++;
			nColumn += 2;
			cRow += 2;
			nRow = cRow;
		}

		g.setColor(Color.white);
		g.drawLine(cColumn * cellX, cRow * cellY, nColumn * cellX, nRow * cellY);
	}

	int width; // width of panel in pixels
	int height; // height of panel in pixels
	int cols; // number of cells width the maze is
	int rows; // number of cells high the maze is
	int cellX; // length of a horizontal edge
	int cellY; // height of a vertical edge

	protected long randomSeed = 101; /*
										 * fixed seed for our random number
										 * generator
										 */
}

/*
 * Below is essentially code from Weiss' book for the disjoint set class, using
 * union by rank and path compression. Elements in the set are numbered starting
 * at 0.
 */
class DisjSetsFast {
	/*
	 * Construct the disjoint sets object. numElements is the initial number of
	 * disjoint sets.
	 */
	DisjSetsFast(int numElements) {
		s = new int[numElements];
		for (int i = 0; i < s.length; i++)
			s[i] = -1;
	}

	/*
	 * Union two disjoint sets using the height heuristic. For simplicity, we
	 * assume root1 and root2 are distinct and represent set names. root1 is the
	 * root of set 1. root2 is the root of set 2.
	 */
	void union(int root1, int root2) {
		if (s[root2] < s[root1]) // root2 is deeper
			s[root1] = root2; // Make root2 new root
		else {
			if (s[root1] == s[root2])
				s[root1]--; // Update height if same
			s[root2] = root1; // Make root1 new root
		}
	}

	/*
	 * Perform a find with path compression. Error checks omitted again for
	 * simplicity. x is the element being searched for.
	 */
	int find(int x) {
		if (s[x] < 0)
			return x;
		else
			return s[x] = find(s[x]);
	}

	private int[] s;
}
