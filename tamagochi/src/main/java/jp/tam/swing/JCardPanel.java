package jp.tam.swing;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class JCardPanel extends JPanel {

	private static final long serialVersionUID = -7298444520711953363L;
	
	private CardLayout cardLayout;
	
	public JCardPanel() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}
}
