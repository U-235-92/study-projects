package jp.tam.swing;

import java.awt.Dimension;

import javax.swing.BoxLayout;
import javax.swing.JPanel;

import jp.tam.util.GameConstructor;

public class JMenuPanel extends JPanel {

	private static final long serialVersionUID = 3107225365655169199L;
	
	public JMenuPanel() {
		BoxLayout box_layout = new BoxLayout(this, BoxLayout.Y_AXIS);
		setLayout(box_layout);
		setPreferredSize(new Dimension(GameConstructor.SCREEN_WIDTH, GameConstructor.SCREEN_HEIGHT));
		setName(getClass().getSimpleName());
	}
}
