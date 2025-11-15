package aq.snake.util;

import java.awt.Color;
import java.awt.Graphics;

public class BigFeed extends Feed {

	public BigFeed(int x, int y, Randomiser randomiser) {
		super(x, y, randomiser);
	}

	@Override
	public void paint(Graphics g) {
		if(randomiser.isBigFeed()) {
			g.setColor(Color.YELLOW);
			g.fillOval(x, y, 20, 20);
		}
	}

}
