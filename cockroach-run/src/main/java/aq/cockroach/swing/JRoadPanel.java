package aq.cockroach.swing;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import aq.cockroach.core.Cockroach;

public class JRoadPanel extends JPanel {
	
	private static final long serialVersionUID = 2985139362569684158L;
	
	private final int HEIGHT_ROAD = 100;
	private final int WIDTH_ROAD = 600;
	
	private int roadNumber;
	
	private Cockroach cockroach;
	
	private JLabel label;
	
	public JRoadPanel(int roadNumber) {
		this.roadNumber = roadNumber;
		
		setLayout(new BoxLayout(this, BoxLayout.Y_AXIS));
		
		JTextField jtf = new JTextField();
		jtf.setMaximumSize(new Dimension(90, 20));
		jtf.setAlignmentX(Component.LEFT_ALIGNMENT);
		
		JButton button = new JButton("Set name");
		button.setAlignmentX(Component.LEFT_ALIGNMENT);
		button.addActionListener((ae) -> {
			cockroach.setName(jtf.getText());
			label.setText(cockroach.getName());
			jtf.setText("");
		});
		
		add(jtf);
		add(button);
		
		addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				if(cockroach.getGui_constructror().isGameStarted()) {
					if(e.getClickCount() == 2) {
						if(e.getX() < cockroach.getX() + Cockroach.COCKROACH_WIDTH && e.getX() >= cockroach.getX()
								&& e.getY() > cockroach.getY() && e.getY() <= cockroach.getY() + Cockroach.COCKROACH_HEIGHT) {
							cockroach.setX(cockroach.getX() + 5);
						}
					}
				}
			}
		});
	}
	
	public int getRoadNumber() {
		return roadNumber;
	}

	public void setCockroach(Cockroach cockroach) {
		this.cockroach = cockroach;
		label = new JLabel(cockroach.getName());
		label.setAlignmentX(Component.LEFT_ALIGNMENT);
		add(label);
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		
		g.setColor(Color.BLACK);
		g.drawRect(100, 0 * HEIGHT_ROAD, WIDTH_ROAD - 1, HEIGHT_ROAD);
		g.drawString("#" + roadNumber, 500, HEIGHT_ROAD / 2);
		
		g.drawString("F", 590, HEIGHT_ROAD / 6);
		g.drawString("I", 590, HEIGHT_ROAD / 3);
		g.drawString("N", 590, HEIGHT_ROAD / 2);
		g.drawString("I", 590, (int) (HEIGHT_ROAD / 1.45));
		g.drawString("S", 590, (int) (HEIGHT_ROAD / 1.2));
		g.drawString("H", 590, (int) (HEIGHT_ROAD / 1.02));
		
		cockroach.paint(g);
	}
}