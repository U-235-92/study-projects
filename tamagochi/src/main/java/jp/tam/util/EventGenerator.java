package jp.tam.util;

import java.awt.EventQueue;
import java.util.concurrent.TimeUnit;

import jp.tam.swing.JGamePanel;

public class EventGenerator implements Runnable {
	
	public static final int DEFAULT_EVENT = EIDMode.DEFAULT_MODE;
	public static final int SHIT_EVENT = EIDMode.SHIT_MODE;
	public static final int ZZZ_EVENT = EIDMode.ZZZ_MODE;
	public static final int FEED_EVENT = EIDMode.FEED_MODE;
	public static final int MOOD_EVENT = EIDMode.MOOD_MODE;
	public static final int DEATH_EVENT = EIDMode.DEATH_MODE;
	
	private static EventGenerator eventGenerator;
	
	private int currentEventCode = DEFAULT_EVENT;
	
	private int currentEventAnimationCode = DEFAULT_EVENT;
	
	private volatile boolean isAllowGenerate = true;
	
	private JGamePanel jGamePNL;
	
	private EventGenerator(JGamePanel jGamePNL) {
		this.jGamePNL = jGamePNL;
	}
	
	public static EventGenerator getEventGenerator(JGamePanel jGamePNL) {
		if(eventGenerator == null) {
			eventGenerator = new EventGenerator(jGamePNL);
		}
		return eventGenerator;
	}
	
	@Override
	public void run() {
		while(true) {
			try {
				while(!isAllowGenerate) {
					synchronized(this) {
						wait();
					}
				}
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			try {
				TimeUnit.MILLISECONDS.sleep(10_000 + (int)(Math.random() * 20_000) + 1);
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			currentEventCode = generateEvent();
			isAllowGenerate = false;
			EventQueue.invokeLater(() -> {
				jGamePNL.repaint();
			});
			countdown();
		}
	}
	
	private void countdown() {
		int timout = 25;
		currentEventAnimationCode = currentEventCode;
		while(timout-- != 0 || currentEventCode != EIDMode.DEFAULT_MODE) {
			if(timout == 0 && currentEventCode != EIDMode.DEFAULT_MODE) {
				currentEventCode = EIDMode.DEATH_MODE;
				jGamePNL.repaint();
				break;
			} else if(timout > 0 && currentEventCode == EIDMode.DEFAULT_MODE) {
				try { 
					while(!isAllowGenerate) {
						synchronized(this) {
							wait();
						}
					}
				} catch(InterruptedException exc) {
					exc.printStackTrace();
				}
				synchronized(this) {	
					notifyAll();
				}
				break;
			}
			try {	
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
		}
	}
	
	private int generateEvent() {
		int eventCode = 1 + (int)((Math.random() * 4));
		if(eventCode == this.currentEventCode) {
			while(eventCode == this.currentEventCode) {
				eventCode = 1 + (int)((Math.random() * 4));
			}
		}
		return eventCode;
	}
	
	public int getAnimationCode() {
		return currentEventAnimationCode;
	}
	
	public int getCurrentEventCode() {
		return currentEventCode;
	}
	
	public void setCurrentEventCode() {
		this.currentEventCode = DEFAULT_EVENT;
	}

	public boolean isAllowGenerateEvent() {
		return isAllowGenerate;
	}
	
	public void setAllowGenerateEvent() {
		isAllowGenerate = true;
	}
}
