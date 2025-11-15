package aq.snake.util;

import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.TimeUnit;

import aq.snake.swing.JGameplayPanel;
import aq.snake.swing.JMainFrame;
import aq.snake.swing.JScorePanel;

public class Snake implements Runnable {

	public static final int SLOW_SPEED = 700;
	public static final int MIDDLE_SPEED = 300;
	public static final int HEIGHT_SPEED = 50;
	
	public static final int DEFAULT_X = JMainFrame.WIDTH / 2;
	public static final int DEFAULT_Y = JMainFrame.HEIGHT / 2;
	
	public static final String UP = "up";
	public static final String DOWN = "down";
	public static final String LEFT = "left";
	public static final String RIGHT = "right";
	
	private int score;
	private int speed;
	
	private boolean isDead = false;
	
	private String name;
	private String move;
	private String currentDirection;
	
	private List<Cell> snakeBody;
	private List<Cell> defaultBody;
	
	private static Snake snake;
	
	private JGameplayPanel jGamePnl;
	private JScorePanel jScorePnl;
	
	private Randomiser randomiser;
	
	private Snake(int speed, JGameplayPanel jGamePnl, JScorePanel jScorePnl, Randomiser randomiser) {
		snakeBody = new CopyOnWriteArrayList<>();
		name = "Default";
		move = LEFT;
		currentDirection = LEFT;
		
		this.speed = speed;
		this.jGamePnl = jGamePnl;
		this.jScorePnl = jScorePnl;
		this.randomiser = randomiser;
		
		Cell head = new Cell();
		Cell body1 = new Cell();
		Cell body2 = new Cell();
		Cell body3 = new Cell();
		Cell body4 = new Cell();
		Cell body5 = new Cell();
		Cell body6 = new Cell();
		Cell body7 = new Cell();
		
		head.setLocation(JMainFrame.WIDTH / 2, JMainFrame.HEIGHT / 2);
		body1.setLocation(JMainFrame.WIDTH / 2 + 10, JMainFrame.HEIGHT / 2);
		body2.setLocation(JMainFrame.WIDTH / 2 + 20, JMainFrame.HEIGHT / 2);
		body3.setLocation(JMainFrame.WIDTH / 2 + 30, JMainFrame.HEIGHT / 2);
		body4.setLocation(JMainFrame.WIDTH / 2 + 40, JMainFrame.HEIGHT / 2);
		body5.setLocation(JMainFrame.WIDTH / 2 + 50, JMainFrame.HEIGHT / 2);
		body6.setLocation(JMainFrame.WIDTH / 2 + 60, JMainFrame.HEIGHT / 2);
		body7.setLocation(JMainFrame.WIDTH / 2 + 70, JMainFrame.HEIGHT / 2);
		
		snakeBody.add(head);
		snakeBody.add(body1);
		snakeBody.add(body2);
		snakeBody.add(body3);
		snakeBody.add(body4);
		snakeBody.add(body5);
		snakeBody.add(body6);
		snakeBody.add(body7);
		
		defaultBody = new ArrayList<>();
		defaultBody.addAll(snakeBody);
	}
	
	public static Snake createSnake(int speed, JGameplayPanel jGamePnl, JScorePanel jScorePnl,
			Randomiser randomiser) {
		if(snake == null) {
			snake = new Snake(speed, jGamePnl, jScorePnl, randomiser);
		}
		return snake;
	}
	
	public void move() {
		Cell first = snakeBody.get(0);
		Cell tmp = new Cell();
		if(move.equals(UP)) {
			tmp.setLocation(first.x, first.y - 10);
		} else if(move.equals(DOWN)) {
			tmp.setLocation(first.x, first.y + 10);
		} else if(move.equals(RIGHT)) {
			tmp.setLocation(first.x + 10, first.y);
		} else if(move.equals(LEFT)) {
			tmp.setLocation(first.x - 10, first.y);
		}
		if(tmp.x > JMainFrame.WIDTH) {
			tmp.x = 0;
		}
		if(tmp.x < 0) {
			tmp.x = JMainFrame.WIDTH;
		}
		if(tmp.y > JMainFrame.HEIGHT - 150) {
			tmp.y = 0;
		}
		if(tmp.y < 0) {
			tmp.y = JMainFrame.HEIGHT - 150;
		}
		if(randomiser.getFeedList().size() != 0) {
			if(randomiser.getFeedList().get(0) instanceof SmalFeed) {
				if(randomiser.getFeedList().get(0).x == snakeBody.get(0).x &&
						randomiser.getFeedList().get(0).y == snakeBody.get(0).y) {
					growUp();
				}
			} else if(randomiser.getFeedList().get(0) instanceof BigFeed) {
				if(snakeBody.get(0).x <= randomiser.getFeedList().get(0).x + 10 &&
						snakeBody.get(0).x >= randomiser.getFeedList().get(0).x - 10 &&
						snakeBody.get(0).y <= randomiser.getFeedList().get(0).y + 10 &&
						snakeBody.get(0).y >= randomiser.getFeedList().get(0).y - 10) {
					growUp();
				}
			}
		}
		snakeBody.add(0, tmp);
		snakeBody.remove(snakeBody.size() - 1);
		for(int i = 1; i < snakeBody.size(); i++) {
			if(snakeBody.get(0).x == snakeBody.get(i).x &&
					snakeBody.get(0).y == snakeBody.get(i).y) {
				randomiser.getFeedList().removeAll(randomiser.getFeedList());
				isDead = true;
				randomiser.setBigFeed(false);
			}
		}
	}
	
	public void growUp() {
		Cell cell = new Cell();
		cell.setLocation(snakeBody.get(snakeBody.size() - 1).x + 10, snakeBody.get(snakeBody.size() - 1).y);
		snakeBody.add(cell);
		if(randomiser.getFeedList().get(0) instanceof SmalFeed) {
			if(speed == SLOW_SPEED) {
				score += Feed.SMALL_FEED_SCORE_SLOW; 
				EventQueue.invokeLater(() -> {
					jScorePnl.repaint();
				});
			} else if(speed == MIDDLE_SPEED) {
				score += Feed.SMALL_FEED_SCORE_MIDDLE;
				EventQueue.invokeLater(() -> {
					jScorePnl.repaint();
				});
			} else {
				score += Feed.SMALL_FEED_SCORE_HIGHT;
				EventQueue.invokeLater(() -> {
					jScorePnl.repaint();
				});
			}
		} else if(randomiser.getFeedList().get(0) instanceof BigFeed) {
			if(randomiser.getTimeLifeBonus() == 0) {
				score += 1;
			} else {
				score += Feed.BIG_FEED_SCORE - (Feed.BIG_FEED_SCORE / randomiser.getTimeLifeBonus());
			}
			EventQueue.invokeLater(() -> {
				jScorePnl.repaint();
			});
		}
		randomiser.getFeedList().removeAll(randomiser.getFeedList());
		randomiser.setBigFeed(false);
		EventQueue.invokeLater(() -> {
			jScorePnl.repaint();
		});
		synchronized (randomiser) {
			randomiser.notifyAll();
		}
	}
	
	public int getScore() {
		return score;
	}
	
	public void setScoreZero() {
		this.score = 0;
	}
	public int getSpeed() {
		return speed;
	}
	
	public String getName() {
		return name;
	}
	
	public String getMove() {
		return move;
	}
	
	public void setMove(String move) {
		this.move = move;
	}
	
	public String getCurrentDirection() {
		return currentDirection;
	}

	public void setCurrentDirection(String currentDirection) {
		this.currentDirection = currentDirection;
	}

	public List<Cell> getSnakeBody() {
		return snakeBody;
	}
	
	public boolean isDead() {
		return isDead;
	}

	public void setDead(boolean isDead) {
		this.isDead = isDead;
	}

	public List<Cell> getDefaultBody() {
		return defaultBody;
	}

	public void paint(Graphics g) {
		for(Cell cell : snakeBody) {
			cell.paint(g);
		}
	}

	@Override
	public void run() {
		while(!isDead) {
			move();
			try {
				TimeUnit.MILLISECONDS.sleep(speed);
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			EventQueue.invokeLater(() -> {
				
				jGamePnl.repaint();
			});
		}
	}
}
