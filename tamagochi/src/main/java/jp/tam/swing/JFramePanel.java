package jp.tam.swing;

import java.awt.Dimension;

import javax.swing.JPanel;

import jp.tam.util.GameConstructor;

public class JFramePanel extends JPanel {

	private static final long serialVersionUID = -3624432055257468430L;

	public JFramePanel() {
		setName(getClass().getSimpleName());
		setPreferredSize(new Dimension(GameConstructor.SCREEN_WIDTH, GameConstructor.SCREEN_HEIGHT));
	}
}
