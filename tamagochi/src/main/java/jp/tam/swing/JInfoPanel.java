package jp.tam.swing;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import jp.tam.util.GameConstructor;

public class JInfoPanel extends JPanel {

	private static final long serialVersionUID = 8239786719072393245L;
	
	public JInfoPanel() {
		setLayout(new GridLayout(1, 4));
		setPreferredSize(new Dimension(GameConstructor.SCREEN_WIDTH, GameConstructor.SCREEN_HEIGHT - 320));
	}
}
