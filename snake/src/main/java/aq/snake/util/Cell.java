package aq.snake.util;

import java.awt.Color;
import java.awt.Graphics;

public class Cell {
	
	protected int x, y;
	
	public Cell() { super(); }
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
	
	public void setLocation(int x, int y) {
		this.x = x;
		this.y = y;
	}
	
	public void paint(Graphics g) {
		g.setColor(Color.RED);
		g.fillRect(x, y, 10, 10);
		g.setColor(Color.YELLOW);
		g.drawLine(x + 5, y + 5, x + 5, y + 5);
	}
}
