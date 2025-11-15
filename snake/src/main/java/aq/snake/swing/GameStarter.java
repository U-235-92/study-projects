package aq.snake.swing;

import java.awt.BorderLayout;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;

import javax.swing.SwingUtilities;

import aq.snake.util.Randomiser;
import aq.snake.util.Snake;

public class GameStarter {
	
	private boolean menuMode = true;
	
	private JMainFrame jMainFrm;
	
	private JMainPanel jMainPnl;
	private JCardPanel jCardPnl;
	private JScorePanel jScorPnl;
	private JMenuPanel jMenuPnl;
	private JGameplayPanel jGamePnl;
	
	private Snake snake;
	
	private Randomiser randomiser;

	public void startGame() {
		SwingUtilities.invokeLater(() -> {
			jMainFrm = new JMainFrame();
			
			jMainPnl = new JMainPanel();
			jCardPnl = new JCardPanel();
			jScorPnl = new JScorePanel();
			jMenuPnl = new JMenuPanel();
			jGamePnl = new JGameplayPanel(this);
			
			randomiser = Randomiser.getRandomiser(jGamePnl);
			randomiser.setjScorePnl(jScorPnl);
			
			jScorPnl.setRandomiser(randomiser);
			
			jCardPnl.add(jMenuPnl, jMenuPnl.getName());
			jCardPnl.add(jGamePnl, jGamePnl.getName());
			
			jMainPnl.add(jScorPnl, BorderLayout.NORTH);
			jMainPnl.add(jCardPnl, BorderLayout.CENTER);
			
			jMainFrm.add(jMainPnl);
			
			jMainFrm.addKeyListener(new KeyAdapter() {
				public void keyPressed(KeyEvent e) {
					if(e.getKeyCode() == KeyEvent.VK_UP) {
						if(menuMode) {
							jMenuPnl.getSwitcher().switchIndex(1);
							jMenuPnl.repaint();
						} else {
							if(snake.getSnakeBody().get(0).getX() > 0 && snake.getSnakeBody().get(0).getX() <= JMainFrame.WIDTH &&
									snake.getSnakeBody().get(0).getY() > 0 && snake.getSnakeBody().get(0).getY() <= JMainFrame.HEIGHT) {
								if(!snake.getCurrentDirection().equals(Snake.DOWN)) {
									snake.setMove(Snake.UP);
									snake.setCurrentDirection(Snake.UP);
								}
							}
						}
					}
					if(e.getKeyCode() == KeyEvent.VK_DOWN) {
						if(menuMode) {
							jMenuPnl.getSwitcher().switchIndex(-1);
							jMenuPnl.repaint();
						} else {
							if(snake.getSnakeBody().get(0).getX() > 0 && snake.getSnakeBody().get(0).getX() <= JMainFrame.WIDTH &&
									snake.getSnakeBody().get(0).getY() > 0 && snake.getSnakeBody().get(0).getY() <= JMainFrame.HEIGHT) {
								if(!snake.getCurrentDirection().equals(Snake.UP)) {
									snake.setMove(Snake.DOWN);
									snake.setCurrentDirection(Snake.DOWN);
								}
							}
						}
					}
					if(e.getKeyCode() == KeyEvent.VK_LEFT) {
						if(!menuMode) {
							if(snake.getSnakeBody().get(0).getX() > 0 && snake.getSnakeBody().get(0).getX() <= JMainFrame.WIDTH &&
									snake.getSnakeBody().get(0).getY() > 0 && snake.getSnakeBody().get(0).getY() <= JMainFrame.HEIGHT) {
								if(!snake.getCurrentDirection().equals(Snake.RIGHT)) {
									snake.setMove(Snake.LEFT);
									snake.setCurrentDirection(Snake.LEFT);
								}
							}
						}
					}
					if(e.getKeyCode() == KeyEvent.VK_RIGHT) {
						
						if(!menuMode) {
							if(snake.getSnakeBody().get(0).getX() > 0 && snake.getSnakeBody().get(0).getX() <= JMainFrame.WIDTH &&
									snake.getSnakeBody().get(0).getY() > 0 && snake.getSnakeBody().get(0).getY() <= JMainFrame.HEIGHT) {
								if(!snake.getCurrentDirection().equals(Snake.LEFT)) {		
									snake.setMove(Snake.RIGHT);
									snake.setCurrentDirection(Snake.RIGHT);
								}
							}
						} 
					}
					if(e.getKeyCode() == KeyEvent.VK_ESCAPE) {
						if(menuMode) {
							System.exit(0);
						} else {
							if(snake.isDead()) {
								snake.getSnakeBody().removeAll(snake.getSnakeBody());
								snake.getSnakeBody().addAll(snake.getDefaultBody());
								snake.setCurrentDirection(Snake.LEFT);
								snake.setMove(Snake.LEFT);
								snake.setScoreZero();
								snake.setDead(false);
								
								randomiser.setCountFeedZero();								
								
								jGamePnl.repaint();
								jScorPnl.repaint();
								
								Thread snakeTh = new Thread(snake);
								snakeTh.start();
								
								Thread randomiserTh = new Thread(randomiser);
								randomiserTh.start();
							}
						}
					}
					if(e.getKeyCode() == KeyEvent.VK_ENTER) {
						if(menuMode) {
							if(jMenuPnl.getSwitcher().getIndex() == 1) {
								snake = Snake.createSnake(Snake.SLOW_SPEED, jGamePnl, jScorPnl, randomiser);
								jScorPnl.setSnake(snake);
								jScorPnl.repaint();
							} else if(jMenuPnl.getSwitcher().getIndex() == 2) {
								snake = Snake.createSnake(Snake.MIDDLE_SPEED, jGamePnl, jScorPnl, randomiser);
								jScorPnl.setSnake(snake);
								jScorPnl.repaint();
							} else if(jMenuPnl.getSwitcher().getIndex() == 3) {
								snake = Snake.createSnake(Snake.HEIGHT_SPEED, jGamePnl, jScorPnl, randomiser);
								jScorPnl.setSnake(snake);
								jScorPnl.repaint();
							} 
							jCardPnl.getCardLayout().show(jCardPnl, jGamePnl.getName());
							menuMode = false;
							
							Thread snakeTh = new Thread(snake);
							snakeTh.start();
							
							Thread randomiserTh = new Thread(randomiser);
							randomiserTh.start();
						} 
					}
				}
			});
		});
	}
	
	public Snake getSnake() {
		if(snake == null) {
			return Snake.createSnake(Snake.SLOW_SPEED, jGamePnl, jScorPnl, randomiser);
		}
		return snake;
	}
}
