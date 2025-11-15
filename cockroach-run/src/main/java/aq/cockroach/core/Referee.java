package aq.cockroach.core;

import java.util.concurrent.TimeUnit;

import javax.swing.JButton;

import aq.cockroach.swing.GUIConstructror;

public class Referee extends Thread {
	
	private GUIConstructror guiConstructor;
	
	private JButton button;
	
	private int countdown = 3;
	
	public Referee(GUIConstructror gui_constructor, JButton button) {
		this.guiConstructor = gui_constructor;
		this.button = button;
	}
	
	@Override
	public void run() {
		while(countdown != 0) {
			button.setEnabled(false);
			System.out.println(countdown);
			countdown--;
			try {
				TimeUnit.MILLISECONDS.sleep(1000);
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
		}
		System.out.println("Start!");
		button.setEnabled(true);
		for(Cockroach c : guiConstructor.getCockroachSet()) {
			Thread th = new Thread(c);
			th.start();
		}
		int max = 0;
		while(guiConstructor.isGameStarted()) {
			for(Cockroach c : guiConstructor.getCockroachSet()) {
				if(c.getX() > max) {
					max = c.getX();
					c.setLeader(true);
					if(c.isLeader()) {
						guiConstructor.getjLeaderLBL().setText("Current leader is: " + c.getName());
					}
				}
			}
		}
	}
}
