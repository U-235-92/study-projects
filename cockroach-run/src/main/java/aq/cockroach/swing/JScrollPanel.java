package aq.cockroach.swing;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Rectangle;

import javax.swing.JPanel;
import javax.swing.Scrollable;

public class JScrollPanel extends JPanel implements Scrollable {
	
	private static final long serialVersionUID = -3641569911142573633L;
	
	public static final int HEIGHT_ROAD = 100;
	public static final int WIDTH_ROAD = 600;
	
	private int count_road;
	
	public JScrollPanel() {
		count_road = 0; 
		setLayout(new GridLayout(count_road + 1, 1));
	}
	
	public int getCount_road() {
		return count_road;
	}
	
	public void setCount_road(int count_road) {
		this.count_road = count_road; 
		setLayout(new GridLayout(count_road + 1, 1));
	}

	@Override
	public Dimension getPreferredSize() {
		return new Dimension(WIDTH_ROAD, (count_road + 1) * HEIGHT_ROAD);
	}

	@Override
	public Dimension getPreferredScrollableViewportSize() {
		return getPreferredSize();
	}

	@Override
	public int getScrollableUnitIncrement(Rectangle visibleRect, int orientation, int direction) {
		return HEIGHT_ROAD;
	}

	@Override
	public int getScrollableBlockIncrement(Rectangle visibleRect, int orientation, int direction) {
		return HEIGHT_ROAD;
	}

	@Override
	public boolean getScrollableTracksViewportWidth() {
		return false;
	}

	@Override
	public boolean getScrollableTracksViewportHeight() {
		return false;
	}
}
