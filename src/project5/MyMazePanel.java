package project5;

import java.awt.Color;
import java.awt.Graphics;
import java.util.Random;

import javax.swing.JPanel;

public class MyMazePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	public int cols;
	public int rows;
	public int cellX;
	public int cellY;
	public long rval = 101;
	public static Color bgColor = Color.DARK_GRAY;
	public static Color lineColor = Color.WHITE;
	private static int NEXT_CELL = 2;

	MyMazePanel(int cols, int rows) {
		this.cols = cols;
		this.rows = rows;
		this.cellX = MyMazeMain.MAZE_WIDTH / (cols + 5);
		this.cellY = MyMazeMain.MAZE_HEIGHT / (rows + 5);
	}

	private void gridDraw(Graphics g) {
		g.setColor(lineColor);
		int i, j, x = cellX, y = cellY;
		for (y = cellY, i = 0; i <= rows; i++, y += cellY) {
			for (x = cellX, j = 0; j <= cols; j++, x += cellX) {
				if (j < cols) {
					g.drawLine(x, y, x + cellX, y);
				}
				if (i < rows) {
					g.drawLine(x, y, x, y + cellY);
				}
			}
		}
		// Start and End points
		g.setColor(bgColor);
		g.drawLine(cellX, cellY, cellX, cellY + cellY);
		g.drawLine(x - cellX, y - cellY, x - cellX, y - (cellY * 2));
	}

	void getDisjointSets(Graphics g) {
		int grid = cols * rows;
		int count = grid;
		DisjointSets connectedCells = new DisjointSets(grid);
		Random rn = new Random(rval);

		while (count > 1) {
			int currCell = rn.nextInt(grid);
			int dir = rn.nextInt(2);
			int neighCell = currCell;
			int val;

			if (dir > 0) {
				val = currCell + cols;
				if (val < grid)
					neighCell = val;
			} else if (currCell % cols < cols - 1)
				neighCell++;

			int neighRoot = connectedCells.find(neighCell);
			int currRoot = connectedCells.find(currCell);
			if (neighRoot != currRoot) {
				connectedCells.union(neighRoot, currRoot);
				removeLine(g, currCell, neighCell);
				// System.out.println("curr:"+curr+" neigh: "+neighCell);
				count--;
			}
		}
	}

	public void removeLine(Graphics g, int cell, int neighbour) {
		int currRow = cell / cols;
		int currCol = cell % cols;
		int rown = neighbour / cols;
		int coln = neighbour % cols;

		if (currRow == rown) {
			rown += NEXT_CELL;
			currCol += NEXT_CELL;
			coln = currCol;
			currRow++;
		} else {
			currCol++;
			coln += NEXT_CELL;
			currRow += NEXT_CELL;
			rown = currRow;
		}
		g.setColor(MyMazePanel.bgColor);
		g.drawLine(currCol * cellX, currRow * cellY, coln * cellX, rown * cellY);
	}

	public void paintComponent(Graphics g) {
		g.setColor(MyMazePanel.lineColor);
		gridDraw(g);
		getDisjointSets(g);
		System.out.println("Maze Created...");
	}
}