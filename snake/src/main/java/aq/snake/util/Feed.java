package aq.snake.util;

import java.awt.Graphics;

public abstract class Feed {

	public static final int SMALL_FEED_SCORE = 10;
	public static final int BIG_FEED_SCORE = 1000;
	
	public static final int SMALL_FEED_SCORE_SLOW = 1;
	public static final int SMALL_FEED_SCORE_MIDDLE = 6;
	public static final int SMALL_FEED_SCORE_HIGHT = 17;
	
	public static final int TIMEOUT_BIG_FEED = 10000;
	
	protected int x;
	protected int y;
	
	protected Randomiser randomiser;
	
	public Feed(int x, int y, Randomiser randomiser) {
		this.x = x;
		this.y = y;
		this.randomiser = randomiser;
	}
	
	public abstract void paint(Graphics g);
	
	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}
