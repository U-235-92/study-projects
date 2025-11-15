package aq.snake.util;

import java.awt.EventQueue;
import java.util.*;
import java.util.concurrent.TimeUnit;

import aq.snake.swing.JGameplayPanel;
import aq.snake.swing.JMainFrame;
import aq.snake.swing.JScorePanel;

public class Randomiser implements Runnable {

	private volatile List<Feed> feedList;
	
	private JGameplayPanel jGamePnl;
	private JScorePanel jScorePnl;
	
	private static Randomiser randomiser;
	
	private int countGenFeed = 1;
	private int timeLifeBonus;
	
	private boolean isBigFeed = false;
	
	private Randomiser(JGameplayPanel jGamePnl) {
		feedList = new ArrayList<>();
		this.jGamePnl = jGamePnl;
	}
	
	private void genFeed() {
		Feed feed;
		int x = 0, y = 0;
		x = (int) (0 + Math.random() * (JMainFrame.WIDTH - 10));
		y = (int)(0 + Math.random() * (JMainFrame.HEIGHT - 150));
		if(x % 10 != 0) {
			int tmpX = x;
			int deltaX = x - (tmpX - x % 10);
			x = tmpX - deltaX;
		}
		if(y % 10 != 0) {
			int tmpY = y;
			int deltaY = y - (tmpY - y % 10);
			y = tmpY - deltaY;
		}
		
		if(countGenFeed % 2 == 0) { 
			isBigFeed = true;
			int tmpTimeLifeBonus = 10;
			timeLifeBonus = tmpTimeLifeBonus;
			feed = new BigFeed(x, y, this);
			feedList.add(feed);
			jGamePnl.setFeed(feed);
			EventQueue.invokeLater(() -> {
				jGamePnl.repaint();
			});
			new Thread(() -> {
				while(isBigFeed && !jGamePnl.getStarter().getSnake().isDead()) {
					timeLifeBonus--;
					if(feedList.size() > 0) {
						if(!(feedList.get(0) instanceof BigFeed)) {
							isBigFeed = false;
							EventQueue.invokeLater(() -> {
								jScorePnl.repaint();
							});
							break;
						}
					}
					if(timeLifeBonus < 0) {	
						isBigFeed = false;
						EventQueue.invokeLater(() -> {
							jScorePnl.repaint();
						});
						feedList.removeAll(feedList);
						genFeed();
						break;
					}
					try {
						TimeUnit.MILLISECONDS.sleep(1000);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					EventQueue.invokeLater(() -> {
						jScorePnl.repaint();
					});
					timeLifeBonus--;
				}
			}).start();
		} else {
			feed = new SmalFeed(x, y, this);
			feedList.add(feed);
			jGamePnl.setFeed(feed);
			EventQueue.invokeLater(() -> {
				jGamePnl.repaint();
			});
		}
		countGenFeed++;
	}
	
	public static Randomiser getRandomiser(JGameplayPanel jGamePnl) {
		if(randomiser == null) {
			randomiser = new Randomiser(jGamePnl);
		}
		return randomiser;
	}

	@Override
	public void run() {
		while(!jGamePnl.getStarter().getSnake().isDead()) {
			try {
				while(feedList.size() != 0) {
					synchronized(this) {
						wait();
					}
				}
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			genFeed();
		}
	}
	
	public List<Feed> getFeedList() {
		return feedList;
	}
	
	public void setCountFeedZero() {
		this.countGenFeed = 1;
	}

	public boolean isBigFeed() {
		return isBigFeed;
	}

	public void setBigFeed(boolean isBigFeed) {
		this.isBigFeed = isBigFeed;
	}

	public int getTimeLifeBonus() {
		return timeLifeBonus;
	}

	public JScorePanel getjScorePnl() {
		return jScorePnl;
	}

	public void setjScorePnl(JScorePanel jScorePnl) {
		this.jScorePnl = jScorePnl;
	}	
}
