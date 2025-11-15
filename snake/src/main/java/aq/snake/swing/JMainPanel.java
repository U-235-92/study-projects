package aq.snake.swing;

import java.awt.Dimension;

import javax.swing.JPanel;

public class JMainPanel extends JPanel {

	private static final long serialVersionUID = 2353143658637936185L;
	
	public static final int WIDTH = 800;
	public static final int HEIGHT = 600;

	public JMainPanel() {
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
	}
}
