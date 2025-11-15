package aq.snake.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

import aq.snake.util.Randomiser;
import aq.snake.util.Snake;

public class JScorePanel extends JPanel {

	private static final long serialVersionUID = 2620416448906054506L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 100;
	
	private Snake snake;
	
	private Randomiser randomiser;
	
	public JScorePanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 22));
	}
	
	public void setRandomiser(Randomiser randomiser) {
		this.randomiser = randomiser;
	}
	
	public void setSnake(Snake snake) {
		this.snake = snake;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(snake != null) {
			g.setColor(Color.RED);
			g.drawString("Score: " +  snake.getScore(), WIDTH / 2 - 50, HEIGHT / 2 + 10);
		}
		if(randomiser.isBigFeed()) {
			g.setColor(Color.YELLOW);
			g.fillOval(WIDTH / 2 + 150, HEIGHT / 2 - 10, 20, 20);
			g.setColor(Color.RED);
			g.drawString(" " +  randomiser.getTimeLifeBonus(), WIDTH / 2 + 170, HEIGHT / 2 + 8);
		}
	}
}
