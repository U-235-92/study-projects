package aq.snake.swing;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;

import javax.swing.JPanel;

public class JMenuPanel extends JPanel {

	private static final long serialVersionUID = -6658964696107015356L;

	public static final int WIDTH = 800;
	public static final int HEIGHT = 500;
	
	private Switcher switcher;
	
	public JMenuPanel() {		
		setPreferredSize(new Dimension(WIDTH, HEIGHT));
		setBackground(Color.BLACK);
		setName(getClass().getSimpleName());
		setFont(new Font(Font.MONOSPACED, Font.BOLD, 28));		
		switcher = new Switcher();
	}
	
	public Switcher getSwitcher() {		
		return switcher;
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);		
		g.setColor(Color.RED);
		g.drawString("Choise speed:", WIDTH / 2 - 100, HEIGHT / 4 - 50);		
		if(switcher.index == 1) {		
			g.setColor(Color.YELLOW);
			g.drawString("Slow", WIDTH / 2 - 28, HEIGHT / 4);
			g.setColor(Color.RED);
			g.drawString("Middle", WIDTH / 2 - 44, HEIGHT / 4 + 40);
			g.drawString("Fast", WIDTH / 2 - 28, HEIGHT / 4 + 80);
		} 		
		if(switcher.index == 2) {			
			g.setColor(Color.RED);
			g.drawString("Slow", WIDTH / 2 - 28, HEIGHT / 4);
			g.setColor(Color.YELLOW);
			g.drawString("Middle", WIDTH / 2 - 44, HEIGHT / 4 + 40);
			g.setColor(Color.RED);
			g.drawString("Fast", WIDTH / 2 - 28, HEIGHT / 4 + 80);
		} 
		
		if(switcher.index == 3) {		
			g.setColor(Color.RED);
			g.drawString("Slow", WIDTH / 2 - 28, HEIGHT / 4);
			g.drawString("Middle", WIDTH / 2 - 44, HEIGHT / 4 + 40);
			g.setColor(Color.YELLOW);
			g.drawString("Fast", WIDTH / 2 - 28, HEIGHT / 4 + 80);
		}
		
		if(switcher.index == 0) {
			g.setColor(Color.RED);
			g.drawString("Slow", WIDTH / 2 - 28, HEIGHT / 4);
			g.drawString("Middle", WIDTH / 2 - 44, HEIGHT / 4 + 40);
			g.drawString("Fast", WIDTH / 2 - 28, HEIGHT / 4 + 80);
		}
	}
	
	public class Switcher {
		
		private int index = 0;
		public int switchIndex(int patch) {
			if(patch < 0) {
				if(index < 3) {
					index++;
				} else {
					index = 1;
				}
			} else if(patch > 0){
				if(index > 1) {
					index--;
				} else {
					index = 3;
				}
			} else {
				index = 1;
			}
			return index;
		}
		
		public int getIndex() {
			return index;
		}
	}
}
