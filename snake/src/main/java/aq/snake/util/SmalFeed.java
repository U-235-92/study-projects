package aq.snake.util;

import java.awt.Color;
import java.awt.Graphics;

public class SmalFeed extends Feed {
	
	public SmalFeed(int x, int y, Randomiser randomiser) {
		super(x, y, randomiser);
	}

	@Override
	public void paint(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, 10, 10);
	}
}
