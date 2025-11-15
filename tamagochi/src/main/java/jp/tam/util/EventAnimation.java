package jp.tam.util;

import jp.tam.swing.JGamePanel;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;

public class EventAnimation implements Runnable {

	private final static int R = 0x00d20000; 
	private final static int G = 0x0000b400;
	private final static int B = 0x0000008c;
	
	public final static int RGB = R | G | B;
	
	private int[][] matrixAnimShit;
	private int[][] matrixAnimMood;
	private int[][] matrixAnimFeed;
	private int[][] matrixAnimZzz;
	
	private List<Pixel> shitPixelList;
	private List<Pixel> moodPixelList;
	private List<Pixel> feedPixelList;
	private List<Pixel> zzzPixelList;
	
	private EventGenerator eventGenerator;
	
	private JGamePanel jGamePNL;
	
	private volatile boolean isAllowShowAnimation = false;
	
	public EventAnimation(EventGenerator eventGenerator, JGamePanel jGamePNL) {
		
		this.eventGenerator = eventGenerator;
		this.jGamePNL = jGamePNL;
		
		shitPixelList = new ArrayList<>();
		moodPixelList = new ArrayList<>();
		feedPixelList = new ArrayList<>();
		zzzPixelList = new ArrayList<>();
		
		matrixAnimShit = new int[][] 
				{{2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {2,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
				 
		matrixAnimMood = new int[][] 
				{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
				 
		matrixAnimFeed = new int[][] 
				{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,0,0,1,0,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,1,0,0,1,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0,1,1,1,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,1,0,1,1,1,1,1,1,1,1,1,1,1,0,1,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
				
		matrixAnimZzz = new int[][] 
				{{0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,2,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,2,2,2,2,2,2,2,2,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,1,1,1,1,1,1,1,1,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0},
				 {0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0}};
				
		for(int i = 0; i < matrixAnimFeed.length; i++) {
			for(int j = 0; j < matrixAnimFeed[0].length; j++) {
				if(matrixAnimFeed[i][j] == 1) {
					feedPixelList.add(new Pixel(j, i, 1));
				}
				if(matrixAnimZzz[i][j] == 1) {
					zzzPixelList.add(new Pixel(j, i, 1));
				}
				if(matrixAnimZzz[i][j] == 2) {
					zzzPixelList.add(new Pixel(j, i, 2));
				}
				if(matrixAnimShit[i][j] == 1) {
					shitPixelList.add(new Pixel(j, i, 1));
				}
				if(matrixAnimShit[i][j] == 2) {
					shitPixelList.add(new Pixel(j, i, 2));
				}
				if(matrixAnimMood[i][j] == 1) {
					moodPixelList.add(new Pixel(j, i, 1));
				}
			}
		}
	}
	
	public boolean isAllowShowAnimation() {
		return isAllowShowAnimation;
	}

	public void setAllowShowAnimation() {
		isAllowShowAnimation = true;
	}

	public EventGenerator getEventGenerator() {
		return eventGenerator;
	}
	
	public JGamePanel getJGamePanel() {
		return jGamePNL;
	}
	
	@Override
	public void run() {
		if(isAllowShowAnimation) {
			switch(eventGenerator.getAnimationCode()) { 
			case EventGenerator.FEED_EVENT:
				getAnimFeed();
				break;
			case EventGenerator.SHIT_EVENT:
				getAnimShit();
				break;
			case EventGenerator.MOOD_EVENT:
				getAnimMood();
				break;
			case EventGenerator.ZZZ_EVENT:
				getAnimZzz();
				break;
			}
		}
	}

	private void getAnimShit() {
		int[][] matrix_anim_shit_copy = new int[matrixAnimShit.length][matrixAnimShit[0].length];
		for(int i = 0; i < matrix_anim_shit_copy.length; i++) {
			for(int j = 0; j < matrix_anim_shit_copy[0].length; j++) {
				matrix_anim_shit_copy[i][j] = matrixAnimShit[i][j];
			}
		}
		List<Pixel> shitPixelListCopy = new ArrayList<>();
		List<Pixel> pixelColorType1List = new ArrayList<>();
		List<Pixel> pixelColorType2List = new ArrayList<>();
		List<Pixel> pixelToRemoveList = new ArrayList<>();
		shitPixelListCopy.addAll(shitPixelList);
		int rigth_pixel = 0;
		while(rigth_pixel++ <= matrixAnimShit[0].length) {
			for(Pixel pixel : shitPixelListCopy) {
				if(pixel.numPaint == 2) {
					pixel.x++;
					pixelColorType2List.add(pixel);
				} else if(pixel.numPaint == 1) {
					pixelColorType1List.add(pixel);
				}
			}
			for(Pixel p1 : pixelColorType1List) {
				for(Pixel p2 : pixelColorType2List) {
					if(p1.x == p2.x && p1.y == p2.y) {
						pixelToRemoveList.add(p1);
					}
				}
			}
			if(pixelToRemoveList.size() != 0) {
				shitPixelListCopy.removeAll(pixelToRemoveList);
			}
			for(int i = 0; i < matrixAnimShit.length; i++) {
				for(int j = 0; j < matrixAnimShit[0].length; j++) {
					matrixAnimShit[i][j] = 0;
					for(Pixel pixel : shitPixelListCopy) {
						if(pixel.x == j && pixel.y == i && pixel.numPaint == 1) {
							matrixAnimShit[i][j] = 1;
						} else if(pixel.x == j && pixel.y == i && pixel.numPaint == 2) {
							matrixAnimShit[i][j] = 2;
						} 
					}
				}
			}
			EventQueue.invokeLater(() -> {	
				jGamePNL.repaint();
			});
			try {
				TimeUnit.MILLISECONDS.sleep(100);
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			pixelToRemoveList.removeAll(pixelToRemoveList);
			pixelColorType1List.removeAll(pixelColorType1List);
			pixelColorType2List.removeAll(pixelColorType2List);
		}
		matrixAnimShit = matrix_anim_shit_copy;
		shitPixelList.removeAll(shitPixelList);
		for(int i = 0; i < matrixAnimShit.length; i++) {
			for(int j = 0; j < matrixAnimShit[0].length; j++) {
				if(matrixAnimShit[i][j] == 1) {
					shitPixelList.add(new Pixel(j, i, 1));
				}
				if(matrixAnimShit[i][j] == 2) {
					shitPixelList.add(new Pixel(j, i, 2));
				}
			}
		}
		isAllowShowAnimation = false;
		eventGenerator.setAllowGenerateEvent();
		synchronized(eventGenerator) {
			eventGenerator.notifyAll();
		}
		EventQueue.invokeLater(() -> {
			jGamePNL.repaint();
		});
	}
	
	private void getAnimMood() {
		int timeout = 3;
		while(timeout-- != 0) {
			EventQueue.invokeLater(() -> {
				jGamePNL.repaint();
			});
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
		}
		isAllowShowAnimation = false;
		eventGenerator.setAllowGenerateEvent();
		synchronized(eventGenerator) {
			eventGenerator.notifyAll();
		}
		EventQueue.invokeLater(() -> {
			jGamePNL.repaint();
		});
	}
	
	private void getAnimFeed() {
		int[][] matrixAnimFeedCopy = new int[matrixAnimFeed.length][matrixAnimFeed[0].length];
		for(int i = 0; i < matrixAnimFeedCopy.length; i++) {
			for(int j = 0; j < matrixAnimFeedCopy[0].length; j++) {
				matrixAnimFeedCopy[i][j] = matrixAnimFeed[i][j];
			}
		}
		List<Pixel> feedPixelListCopy = new ArrayList<>();
		feedPixelListCopy.addAll(feedPixelList);
		int timout = 5;
		EventQueue.invokeLater(() -> {
			jGamePNL.repaint();
		});
		try {
			TimeUnit.MILLISECONDS.sleep(1000);
		} catch(InterruptedException exc) {
			exc.printStackTrace();
		}
		while(timout-- != 1) {
			int c = (int) (feedPixelList.size() / timout);
			for(int i = 0; i < c; i++) {
				if(timout != 0) {
					int rnd = (int) (Math.random() * ((feedPixelList.size() - 1) + 1));
					feedPixelList.remove(rnd);
				} 
			}
			for(int i = 0; i < matrixAnimFeed.length; i++) {
				for(int j = 0; j < matrixAnimFeed[0].length; j++) {
					matrixAnimFeed[i][j] = 0;
					for(Pixel pixel : feedPixelList) {
						if(pixel.x == j && pixel.y == i && pixel.numPaint == 1) {
							matrixAnimFeed[i][j] = 1;
						} 
					}
				}
			}
			EventQueue.invokeLater(() -> {
				jGamePNL.repaint();
			});
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
		}
		for(int i = 0; i < matrixAnimFeedCopy.length; i++) {
			for(int j = 0; j < matrixAnimFeedCopy[0].length; j++) {
				matrixAnimFeed[i][j] = matrixAnimFeedCopy[i][j];
			}
		}
		feedPixelList.removeAll(feedPixelList);
		feedPixelList.addAll(feedPixelListCopy);
		isAllowShowAnimation = false;
		eventGenerator.setAllowGenerateEvent();
		synchronized(eventGenerator) {
			eventGenerator.notifyAll();
		}
		EventQueue.invokeLater(() -> {
			jGamePNL.repaint();
		});
	}
	
	private void getAnimZzz() {
		int timout = 3;
		int[][] matrixAnimZzzCopy = new int[matrixAnimZzz.length][matrixAnimZzz[0].length];
		for(int i = 0; i < matrixAnimZzzCopy.length; i++) {
			for(int j = 0; j < matrixAnimZzzCopy[0].length; j++) {
				matrixAnimZzzCopy[i][j] = matrixAnimZzz[i][j];
			}
		}
		List<Pixel> zzzPixelListCopy = new ArrayList<>();
		List<Pixel> pixelColorType1List = new ArrayList<>();
		List<Pixel> pixelColorType2List = new ArrayList<>();
		for(Pixel pixel : zzzPixelList) {
			if(pixel.numPaint == 1) {
				pixelColorType1List.add(pixel);
			} else if(pixel.numPaint == 2) {
				pixelColorType2List.add(pixel);
			}
		}
		zzzPixelListCopy.addAll(zzzPixelList);
		zzzPixelList.removeAll(zzzPixelList);
		while(timout-- != 0) {
			for(int i = 0; i < matrixAnimZzz.length; i++) {
				for(int j = 0; j < matrixAnimZzz[0].length; j++) {
					matrixAnimZzz[i][j] = 0;
					for(Pixel pixel : zzzPixelList) {
						if(pixel.x == j && pixel.y == i && pixel.numPaint == 1) {
							matrixAnimZzz[i][j] = 1;
						} 
						if(pixel.x == j && pixel.y == i && pixel.numPaint == 2) {
							matrixAnimZzz[i][j] = 2;
						}
					}
				}
			}
			EventQueue.invokeLater(() -> {
				jGamePNL.repaint();
			});
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			if(timout == 2) {
				zzzPixelList.addAll(pixelColorType1List);
			} else if(timout == 1) {
				zzzPixelList.addAll(pixelColorType2List);
			}
		}
		zzzPixelList.removeAll(zzzPixelList);
		zzzPixelList.addAll(zzzPixelListCopy);
		isAllowShowAnimation = false;
		eventGenerator.setAllowGenerateEvent();
		synchronized(eventGenerator) {
			eventGenerator.notifyAll();
		}
		EventQueue.invokeLater(() -> {
			jGamePNL.repaint();
		});
	}
	
	public void paint(Graphics g) {
		switch(eventGenerator.getAnimationCode()) {
		case EventGenerator.FEED_EVENT:
			for(int y = 0, l = 15; y < matrixAnimFeed.length; y += 1, l += 15) {
				for(int x = 0, k = 15; x < matrixAnimFeed[0].length; x += 1, k += 15) {
					if(matrixAnimFeed[y][x] == 1) {
						g.setColor(new Color(RGB));
						g.fillRect(k + 2, l + 2, 15 - 2, 15 - 2);
					} 
				}
			}
			break;
		case EventGenerator.MOOD_EVENT:
			for(int y = 0, l = 15; y < matrixAnimMood.length; y += 1, l += 15) {
				for(int x = 0, k = 15; x < matrixAnimMood[0].length; x += 1, k += 15) {
					if(matrixAnimMood[y][x] == 1) {
						g.setColor(new Color(RGB));
						g.fillRect(k + 2, l + 2, 15 - 2, 15 - 2);
					} 
				}
			}
			break;
		case EventGenerator.SHIT_EVENT:
			for(int y = 0, l = 15; y < matrixAnimShit.length; y += 1, l += 15) {
				for(int x = 0, k = 15; x < matrixAnimShit[0].length; x += 1, k += 15) {
					if(matrixAnimShit[y][x] == 1 || matrixAnimShit[y][x] == 2) {
						g.setColor(new Color(RGB));
						g.fillRect(k + 2, l + 2, 15 - 2, 15 - 2);
					} 
				}
			}
			break;
		case EventGenerator.ZZZ_EVENT:
			for(int y = 0, l = 15; y < matrixAnimZzz.length; y += 1, l += 15) {
				for(int x = 0, k = 15; x < matrixAnimZzz[0].length; x += 1, k += 15) {
					if(matrixAnimZzz[y][x] == 1 || matrixAnimZzz[y][x] == 2) {
						g.setColor(new Color(RGB));
						g.fillRect(k + 2, l + 2, 15 - 2, 15 - 2);
					} 
				}
			}
			break;
		}
	}
	
	public class Pixel {
		
		private int x;
		private int y;
		private int numPaint;
		
		public Pixel(int x, int y, int paintNumber) {
			this.x = x;
			this.y = y;
			this.numPaint = paintNumber;
		}
		
		public int getX() {
			return x;
		}
		
		public int getY() {
			return y;
		}
		
		public int getNumberPaint() {
			return numPaint;
		}
	}
}
