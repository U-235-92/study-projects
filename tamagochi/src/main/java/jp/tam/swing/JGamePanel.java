package jp.tam.swing;

import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JPanel;

import jp.tam.util.CharacterStore;
import jp.tam.util.GameConstructor;
import jp.tam.util.Switcher;

public class JGamePanel extends JPanel {

	private static final long serialVersionUID = -8876656126384598656L;
	
	private CharacterStore characterStore;
	
	private Switcher switcher;
	
	public JGamePanel(CharacterStore characterStore, Switcher switcher) {
		this.characterStore = characterStore;
		this.switcher = switcher;
		setPreferredSize(new Dimension(GameConstructor.SCREEN_WIDTH, GameConstructor.SCREEN_HEIGHT - 80));
		setName(getClass().getSimpleName());
	}
	
	@Override
	protected void paintComponent(Graphics g) {
		super.paintComponent(g);
		characterStore.getCharacterList().get(switcher.getCharacterIndex()).paint(g);
	}
}
