package aq.cockroach.swing;

import java.util.*;
import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import aq.cockroach.core.Cockroach;
import aq.cockroach.core.Referee;

public class GUIConstructror {

	private volatile boolean isGameStarted = false;
	
	private Set<Cockroach> cockroachSet = new HashSet<>();
	
	private JFrame jMainFRM;
	
	private JButtonPanel jButtonPNL;
	
	private JScrollPanel jScrollPNL;
	private JScrollPane jScrollP;
	
	private JPanel jEnterPNL;
	private JPanel jCardPNL;
		
	private JTextField jEnterTF;
	
	private JLabel jEnterLBL;
	private JLabel jLeaderLBL;
	
	private JButton jEnterBTN;
	private JButton jStartBTN;
	
	private int roadsCount;

	public void runGUI() {
		SwingUtilities.invokeLater(() -> {
			CardLayout cardLayout = new CardLayout();
			
			jMainFRM = new JFrame("Cockroach_Run");
			
			jScrollPNL = new JScrollPanel();
			jCardPNL = new JPanel(cardLayout);
			jEnterPNL = new JPanel();
			jButtonPNL = new JButtonPanel();
			
			jScrollP = new JScrollPane(jScrollPNL);
			
			jEnterTF = new JTextField(10);
			
			jEnterLBL = new JLabel("Enter count of roads:");
			jLeaderLBL = new JLabel();
			
			jEnterBTN = new JButton("Enter");
			jStartBTN = new JButton("Start");
			
			jEnterPNL.setName("Enter");
			jScrollPNL.setName("Main");
			jScrollP.setName("Scroll");
			
			setFrame(jMainFRM);
			
			jEnterPNL.add(jEnterLBL, BorderLayout.CENTER);
			jEnterPNL.add(jEnterTF, BorderLayout.CENTER);
			jEnterPNL.add(jEnterBTN, BorderLayout.CENTER);
			
			jCardPNL.add(jEnterPNL.getName(), jEnterPNL);
			jCardPNL.add(jScrollP.getName(), jScrollP);
			
			jMainFRM.add(jCardPNL);
			
			jEnterBTN.addActionListener((ae) -> {
				boolean wasEnter = false;
				try {
					roadsCount = Integer.valueOf(jEnterTF.getText());
					wasEnter = true;
				} catch(NumberFormatException exc) {
					System.err.println("Invalid data of roads number!");
				}
				jScrollPNL.setCount_road(roadsCount);
				for(int i = 0; i < jScrollPNL.getCount_road(); i++) {
					JRoadPanel roadPanel = new JRoadPanel(i + 1);
					Cockroach cockroach = new Cockroach(101, JScrollPanel.HEIGHT_ROAD / 2, roadPanel, this);
					cockroachSet.add(cockroach);
					roadPanel.setCockroach(cockroach);
					jScrollPNL.add(roadPanel);
				}
				jButtonPNL.add(jStartBTN);
				jScrollPNL.add(jButtonPNL);
				if(wasEnter) {
					cardLayout.show(jCardPNL, jScrollP.getName());
				}
			});
			jStartBTN.addActionListener((ae) -> {
				if(!isGameStarted) {
					isGameStarted = true;
					Referee referee = new Referee(this, jStartBTN);
					referee.start();
					jButtonPNL.add(jLeaderLBL);
					jStartBTN.setText("Restart");
				} else {
					isGameStarted = false;
					jStartBTN.setText("Start");
					for(Cockroach c : cockroachSet) {
						c.setX(Cockroach.START_POINT);
						c.getjRoadPNL().repaint();
					}
				}
			});	
		});
	}

	public int getCount_roads() {
		return roadsCount;
	}

	public Set<Cockroach> getCockroachSet() {
		return cockroachSet;
	}

	public boolean isGameStarted() {
		return isGameStarted;
	}

	public void setGameStarted(boolean isGameStarted) {
		this.isGameStarted = isGameStarted;
	}

	public JLabel getjLeaderLBL() {
		return jLeaderLBL;
	}

	private void setFrame(JFrame jFRM) {
		jFRM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jFRM.setSize(new Dimension(600, 400));
		jFRM.setResizable(false);
		jFRM.setLocationRelativeTo(null);
		jFRM.setVisible(true);
	}
}
