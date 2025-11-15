package aq.snake.swing;

import java.awt.Dimension;

import javax.swing.JFrame;

public class JMainFrame extends JFrame {

	private static final long serialVersionUID = 2175105795354125078L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;
		
	public JMainFrame() {
		setTitle("Snake");
		setSize(new Dimension(WIDTH, HEIGHT));
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		setResizable(false);
		setLocationRelativeTo(null);
		setVisible(true);
	}
}
