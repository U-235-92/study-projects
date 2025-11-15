package jp.tam.util;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.EventQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import jp.tam.character.Character;
import jp.tam.character.*;
import jp.tam.parameter.FeedParm;
import jp.tam.parameter.MoodParm;
import jp.tam.parameter.ShitParm;
import jp.tam.parameter.ZzzParm;
import jp.tam.swing.JCardPanel;
import jp.tam.swing.JFramePanel;
import jp.tam.swing.JGamePanel;
import jp.tam.swing.JInfoPanel;
import jp.tam.swing.JMenuPanel;
import jp.tam.swing.JParmPanel;

public class GameConstructor {
	
	public static final int SCREEN_WIDTH = 600;
	public static final int SCREEN_HEIGHT = 400;
	
	private boolean characterHasChanged = false;
	
	private JFrame jMainFRM;
	
	private JCardPanel jCardPNL;
	
	private JMenuPanel jMenuPNL;
	
	private JFramePanel jFramePNL;
	
	private JInfoPanel jInfoPNL;
	
	private JGamePanel jGamePNL;
	
	private JParmPanel jZzzParmPNL;
	private JParmPanel jFeedParmPNL;
	private JParmPanel jShitParmPNL;
	private JParmPanel jMoodParmPNL;
	
	private ParameterStore parameterStore;
	
	private CharacterStore characterStore;
	
	private Switcher switcher;
	
	private EventGenerator eventGenerator;
	
	private EventAnimation eventAnimation;
	
	private FeedParm feedParm;
	private MoodParm moodParm;
	private ShitParm shitParm;
	private ZzzParm zzzParm;
	
	private JButton jStartMenuBTN;
	private JButton jExitMenuBTN;
	private JButton jExitGameBTN;
	private JButton jNextBTN;
	private JButton jPrevBTN;
	private JButton jEnterBTN;
	
	private Character cat;
	private Character robot;
	private Character spider;
	
	public void constructGame() {
		
		parameterStore = new ParameterStore();
		
		characterStore = new CharacterStore();
		
		switcher = new Switcher(parameterStore, characterStore);
		
		feedParm = FeedParm.getParameter();
		moodParm = MoodParm.getParameter();
		shitParm = ShitParm.getParameter();
		zzzParm = ZzzParm.getParameter();
		
		SwingUtilities.invokeLater(() -> {
			
			jMainFRM = new JFrame("Tamagochi");
			
			jCardPNL = new JCardPanel();
			jMenuPNL = new JMenuPanel();
			jFramePNL = new JFramePanel();
			jInfoPNL = new JInfoPanel();
			jGamePNL = new JGamePanel(characterStore, switcher);
			
			jZzzParmPNL = new JParmPanel(zzzParm, switcher);
			jShitParmPNL = new JParmPanel(shitParm, switcher);
			jFeedParmPNL = new JParmPanel(feedParm, switcher);
			jMoodParmPNL = new JParmPanel(moodParm, switcher);
			
			jStartMenuBTN = new JButton("Start");
			jEnterBTN = new JButton("Enter");
			jNextBTN = new JButton("Next");
			jPrevBTN = new JButton("Prev");
			jExitMenuBTN = new JButton("Exit");
			jExitGameBTN = new JButton("Exit");
			
			eventGenerator = EventGenerator.getEventGenerator(jGamePNL);
			
			eventAnimation = new EventAnimation(eventGenerator, jGamePNL);
			
			cat = Cat.getCharacter(jGamePNL, eventGenerator, eventAnimation);
			robot = Robot.getCharacter(jGamePNL, eventGenerator, eventAnimation);
			spider = Spider.getCharacter(jGamePNL, eventGenerator, eventAnimation);
			
			constructScreen();
			
			jStartMenuBTN.addActionListener((ae) -> {
				jCardPNL.getCardLayout().show(jCardPNL, jFramePNL.getName());
			});
			
			jEnterBTN.addActionListener((ae) -> {
				if(!characterHasChanged) {
					characterHasChanged = true;
					Character choisedCharacter = characterStore.getCharacterList().get(switcher.getCharacterIndex());
					Thread characterThread = new Thread(choisedCharacter);
					characterThread.start();
					Thread eventThread = new Thread(eventGenerator);
					eventThread.start();
				} else {
					if(eventGenerator.getCurrentEventCode() == 
							parameterStore.getParameterList().get(switcher.getParameterIndex()).getParameter().getID()) {
						eventGenerator.setCurrentEventCode();
						eventAnimation.setAllowShowAnimation();
						Thread animationThread = new Thread(eventAnimation);
						animationThread.start();
					}
				}
			});
			
			jNextBTN.addActionListener((ae) -> {
				if(!characterHasChanged) {
					switcher.switchCharacterIndex(1);
					EventQueue.invokeLater(() -> {
						jGamePNL.repaint();
					});
				} else {
					switcher.switchParameterIndex(1);
					EventQueue.invokeLater(() -> {
						for(JParmPanel p : parameterStore.getParameterList()) {
							p.repaint();
						}
					});
				}
			});
			
			jPrevBTN.addActionListener((ae) -> {
				if(!characterHasChanged) {
					switcher.switchCharacterIndex(-1);
					EventQueue.invokeLater(() -> {
						jGamePNL.repaint();
					});
				} else {
					switcher.switchParameterIndex(-1);
					EventQueue.invokeLater(() -> {
						for(JParmPanel p : parameterStore.getParameterList()) {
							p.repaint();
						}
					});
				}
			});
			
			jExitGameBTN.addActionListener((ae) -> {	
				System.exit(0);
			});
			
			jExitMenuBTN.addActionListener((ae) -> {
				System.exit(0);
			});
			
		});
	}
	
	private void constructScreen() {
		
		jMainFRM.setSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
		jMainFRM.setResizable(false);
		jMainFRM.setLocationRelativeTo(null);
		jMainFRM.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jMainFRM.setVisible(true);
		
		jMainFRM.add(jCardPNL);
		
		jCardPNL.add(jMenuPNL.getName(), jMenuPNL);
		jCardPNL.add(jFramePNL.getName(), jFramePNL);
		
		jStartMenuBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
		jExitMenuBTN.setAlignmentX(Component.CENTER_ALIGNMENT);
		
		jMenuPNL.add(Box.createVerticalStrut(100));
		jMenuPNL.add(jStartMenuBTN);
		jMenuPNL.add(Box.createVerticalStrut(10));
		jMenuPNL.add(jExitMenuBTN);
		
		jFramePNL.add(jInfoPNL);
		jFramePNL.add(jGamePNL);
		
		jGamePNL.add(Box.createVerticalStrut(SCREEN_HEIGHT + 100));
		jGamePNL.add(jPrevBTN);
		jGamePNL.add(jNextBTN);
		jGamePNL.add(jEnterBTN);
		jGamePNL.add(jExitGameBTN);
		
		jInfoPNL.add(jFeedParmPNL);
		jInfoPNL.add(jMoodParmPNL);
		jInfoPNL.add(jShitParmPNL);
		jInfoPNL.add(jZzzParmPNL);
		
		parameterStore.add(jFeedParmPNL);
		parameterStore.add(jMoodParmPNL);
		parameterStore.add(jShitParmPNL);
		parameterStore.add(jZzzParmPNL);
		
		characterStore.getCharacterList().add(cat);
		characterStore.getCharacterList().add(robot);
		characterStore.getCharacterList().add(spider);
	}
}
