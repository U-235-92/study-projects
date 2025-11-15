package aq.cockroach.swing;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;
import javax.swing.border.MatteBorder;

public class JButtonPanel extends JPanel {
	
	private static final long serialVersionUID = -6745423645983157104L;

	public JButtonPanel() {
		setLayout(new FlowLayout(FlowLayout.LEFT));
		setBorder(new MatteBorder(1, 0, 0, 0, Color.BLACK));
	}
}
