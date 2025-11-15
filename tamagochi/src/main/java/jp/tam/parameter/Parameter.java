package jp.tam.parameter;

import java.awt.Color;
import java.awt.Graphics;

public abstract class Parameter {
	
	private final static int R_SELECTED = 0x00d20000; 
	private final static int G_SELECTED = 0x0000b400;
	private final static int B_SELECTED = 0x0000008c;
	
	public final static int RGB_SELECTED = R_SELECTED | G_SELECTED | B_SELECTED;
	
	private final static int R_NO_SELECTED = 0x00dc0000;
	private final static int G_NO_SELECTED = 0x0000dc00;
	private final static int B_NO_SELECTED = 0x000000dc;
	
	public final static int RGB_NO_SELECTED = R_NO_SELECTED | G_NO_SELECTED | B_NO_SELECTED;
	
	protected int[][] pixelMatrix;
	
	protected static int counter = -1;
	
	private int id;
	
	protected Parameter() { super(); }
	
	public int getID() {
		return id;
	}
	
	public void setID() {
		counter++;
		id = counter;
	}
	
	public abstract void paint(Graphics g, Color color);
}
