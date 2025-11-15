package aq.snake.swing;

import java.awt.CardLayout;

import javax.swing.JPanel;

public class JCardPanel extends JPanel {

	private static final long serialVersionUID = 906211315405409762L;
	
	private CardLayout cardLayout;
	
	public JCardPanel() {
		cardLayout = new CardLayout();
		setLayout(cardLayout);
	}

	public CardLayout getCardLayout() {
		return cardLayout;
	}
}
