package jp.tam.character;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.concurrent.TimeUnit;

import jp.tam.swing.JGamePanel;
import jp.tam.util.EventAnimation;
import jp.tam.util.EventGenerator;

public abstract class Character implements Runnable {
	
	private final static int R = 0x00d20000; 
	private final static int G = 0x0000b400;
	private final static int B = 0x0000008c;
	
	public final static int RGB = R | G | B;

	protected int[][] pixelMatrixDefault;
	protected int[][] pixelMatrixZzz;
	protected int[][] pixelMatrixFeed;
	protected int[][] pixelMatrixShit;
	protected int[][] pixelMatrixMood;
	protected int[][] pixelMatrixDeath;
	
	protected int rightPoint;
	protected int leftPoint;
	protected int characterWidth;
	
	protected JGamePanel jGamePNL;
	
	protected EventGenerator eventGenerator;
	
	protected EventAnimation eventAnimation;
	
	protected Character(JGamePanel jGamePNL, EventGenerator eventGenerator, EventAnimation eventAnimation) {
		
		this.jGamePNL = jGamePNL;
		this.eventGenerator = eventGenerator;
		this.eventAnimation = eventAnimation;
	}
	
	public JGamePanel getJGamePanel() {
		return jGamePNL;
	}

	public EventGenerator getEventGenerator() {
		return eventGenerator;
	}
	
	public EventAnimation getEventAnimation() {
		return eventAnimation;
	}
	
	protected void updateScreen() {
		EventQueue.invokeLater(() -> {
			jGamePNL.repaint(); 
		});
		try {
			TimeUnit.MILLISECONDS.sleep(500);
		} catch(InterruptedException exc) {
			exc.printStackTrace();
		}
	}
	
	protected void move() {
		boolean isLeftDirection = true;		
		int rightBorder = pixelMatrixDefault[0].length - 1, leftBorder = 0;
		while(true) {			
			try {
				while(eventGenerator.getCurrentEventCode() != EventGenerator.DEFAULT_EVENT && eventAnimation.isAllowShowAnimation()) {	
					synchronized(this) {
						wait();
					}
				}
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			if(isLeftDirection) {	
				if(leftPoint != leftBorder + 1) {
					leftPoint--;
					for(int i = 0; i < pixelMatrixDefault.length; i++) {
						int start = 0, end = 0;
						boolean isMovementStart = false;
						for(int j = pixelMatrixDefault[0].length - 1; j >= 0; j--) {
							if(!isMovementStart) {
								if(pixelMatrixDefault[i][j] == 1) {
									start = j;
									isMovementStart = true;
								}	
							} else {
								if(pixelMatrixDefault[i][j] == 0) {
									end = j;
									int tmp = pixelMatrixDefault[i][start];
									pixelMatrixDefault[i][start] = pixelMatrixDefault[i][end];
									pixelMatrixDefault[i][end] = tmp;
									isMovementStart = false; start = end = 0;
								}	
							}
						}
					}					
					updateScreen();
				} else {
					leftPoint--;
					for(int i = 0; i < pixelMatrixDefault.length; i++) {
						int start = 0, end = 0;
						boolean isStartMovement = false;
						for(int j = pixelMatrixDefault[0].length - 1; j >= 0; j--) {
							if(!isStartMovement) {
								if(pixelMatrixDefault[i][j] == 1) {									
									start = j;
									isStartMovement = true;
								}	
							} else {
								if(pixelMatrixDefault[i][j] == 0) {
									end = j;
									int tmp = pixelMatrixDefault[i][start];
									pixelMatrixDefault[i][start] = pixelMatrixDefault[i][end];
									pixelMatrixDefault[i][end] = tmp;
									isStartMovement = false; start = end = 0;
								}	
							}
						}
					}
					updateScreen();
					isLeftDirection = false;
					rightPoint = characterWidth + 1;
				}
			} else {
				if(rightPoint != rightBorder - 1) {
					rightPoint++;
					for(int i = 0; i < pixelMatrixDefault.length; i++) {
						int start = 0, end = 0;
						boolean isStartMovement = false;
						for(int j = 0; j < pixelMatrixDefault[0].length; j++) {
							if(!isStartMovement) {
								if(pixelMatrixDefault[i][j] == 1) {
									start = j;
									isStartMovement = true;
								}	
							} else {
								if(pixelMatrixDefault[i][j] == 0) {
									end = j;
									int tmp = pixelMatrixDefault[i][end];
									pixelMatrixDefault[i][end] = pixelMatrixDefault[i][start];
									pixelMatrixDefault[i][start] = tmp;
									isStartMovement = false; start = end = 0;
								}	
							}
						}
					}
					updateScreen();
				} else {
					rightPoint++;
					for(int i = 0; i < pixelMatrixDefault.length; i++) {
						int start = 0, end = 0;
						boolean isStartMovement = false;
						for(int j = 0; j < pixelMatrixDefault[0].length; j++) {
							if(!isStartMovement) {
								if(pixelMatrixDefault[i][j] == 1) {
									start = j;
									isStartMovement = true;
								}	
							} else {
								if(pixelMatrixDefault[i][j] == 0) {
									end = j;
									int tmp = pixelMatrixDefault[i][end];
									pixelMatrixDefault[i][end] = pixelMatrixDefault[i][start];
									pixelMatrixDefault[i][start] = tmp;
									isStartMovement = false; start = end = 0;
								}	
							}
						}
					}
					updateScreen();
					isLeftDirection = true;
					leftPoint = rightBorder - characterWidth - 1;
				}
			}
		}
	}
	
	public abstract void paint(Graphics g);
	public abstract void run();
}
