package aq.cockroach.core;

import java.awt.Color;
import java.awt.EventQueue;
import java.awt.Graphics;
import java.util.ArrayList;
import java.util.concurrent.TimeUnit;

import aq.cockroach.swing.GUIConstructror;
import aq.cockroach.swing.JRoadPanel;

public class Cockroach implements Runnable {
	
	private volatile static ArrayList<Cockroach> leader_board = new ArrayList<>();
	
	public static final int START_POINT = 101;
	public static final int COCKROACH_WIDTH = 50;
	public static final int COCKROACH_HEIGHT = 20;
	
	private static int cockroachCounterId;
	
	private int cockroachId;
	
	private int r;
	private int g;
	private int b;
	private int x;
	private int y;
		
	private boolean isFinish;
	private volatile boolean isLeader;
	
	private String name;
	
	private GUIConstructror gui_constructror;
	
	private JRoadPanel jRoadPNL;
	
	public Cockroach(int x, int y, JRoadPanel jRoadPNL, GUIConstructror gui_constructor) {
		cockroachCounterId++;
		cockroachId = cockroachCounterId;
		r = (int)(Math.random() * 255);
		g = (int)(Math.random() * 255);
		b = (int)(Math.random() * 255);
		this.x = x;
		this.y = y;
		this.jRoadPNL = jRoadPNL;
		this.gui_constructror = gui_constructor;
		name = getClass().getSimpleName() + "#" + cockroachCounterId;
	}

	public GUIConstructror getGui_constructror() {
		return gui_constructror;
	}

	public boolean isLeader() {
		return isLeader;
	}

	public void setLeader(boolean isLeader) {
		this.isLeader = isLeader;
	}

	public boolean isFinish() {
		return isFinish;
	}

	public void setFinish(boolean isFinish) {
		this.isFinish = isFinish;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		
		this.name = name;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}

	public JRoadPanel getjRoadPNL() {
		return jRoadPNL;
	}

	@Override
	public boolean equals(Object obj) {
		if(obj == this) {
			return true;
		}
		
		if(obj == null || obj.getClass() != getClass()) {
			return false;
		}
		Cockroach c = (Cockroach) obj;
		return cockroachId == c.cockroachId;
	}
	
	@Override
	public int hashCode() {
		return 31 * name.hashCode() * cockroachId;
	}
	
	@Override
	public void run() {	
		while(x != 600 - COCKROACH_WIDTH) {
			try {
				while(!gui_constructror.isGameStarted()) {	
					synchronized(this) {
						wait();
					}
				}
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			x += 1;
			try {
				TimeUnit.MILLISECONDS.sleep((int)(Math.random() * 150));
			} catch(InterruptedException exc) {
				exc.printStackTrace();
			}
			EventQueue.invokeLater(() -> {
				jRoadPNL.repaint();
			});
		}
		System.out.println(name + " finish!");
		leader_board.add(this);
		if(leader_board.size() == gui_constructror.getCount_roads()) {
			System.out.println("Race was end!");
			System.out.println("Winners:");
			for(int i = 0; i < 3; i++) {
				System.out.println(leader_board.get(i).getName() + " is " + (i + 1) +  "st");
			}
			leader_board.removeAll(leader_board);
		}
	}

	public void paint(Graphics g) {
		g.setColor(new Color(r, this.g, b));
		g.drawOval(x, y, COCKROACH_WIDTH, COCKROACH_HEIGHT);
		
		g.drawLine(x, y - 10, x + 10, y + 3);
		g.drawLine(x + 10, y + 17, x, y + 30);
		
		g.drawLine(x + 25, y + 20, x + 25, y + 33);
		g.drawLine(x + 25, y, x + 25, y - 13);
		
		g.drawLine(x + 50, y - 10, x + 40, y + 3);
		g.drawLine(x + 40, y + 17, x + 50, y + 30);
		
		g.drawLine(x + 40, y + 7, x + 40, y + 7);
		g.drawLine(x + 40, y + 12, x + 40, y + 12);
	}
}
