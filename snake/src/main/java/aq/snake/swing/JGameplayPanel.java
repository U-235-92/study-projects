package aq.snake.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import aq.snake.util.Feed;

public class JGameplayPanel extends JPanel {
	
	private static final long serialVersionUID = 7750235506303901167L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;
	
	private GameStarter starter;
	
	private Feed feed;
	
	public JGameplayPanel(GameStarter starter) {		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		setName(getClass().getSimpleName());
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 34));		
		this.starter = starter;
	}
	
	public void setFeed(Feed feed) {		
		this.feed = feed;
	}
	
	public GameStarter getStarter() {		
		return starter;
	}
	
	@Override
	protected void paintComponent(Graphics g) {	
		super.paintComponent(g);		
		starter.getSnake().paint(g);	
		if(feed != null) {			
			feed.paint(g);
		}		
		if(starter.getSnake().isDead()) {			
			g.setColor(Color.RED);
			g.drawString("GAME OVER", JMainFrame.WIDTH / 2 - 80, JMainFrame.HEIGHT / 4);
			g.drawString("Press \"ESC\" to restart", JMainFrame.WIDTH / 2 - 210, JMainFrame.HEIGHT / 3);
		}
	}
}
