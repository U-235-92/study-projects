package jp.tam.swing;

import java.awt.Color;
import java.awt.Graphics;

import javax.swing.JPanel;

import jp.tam.parameter.Parameter;
import jp.tam.util.Switcher;

public class JParmPanel extends JPanel {

	private static final long serialVersionUID = 8752604986445449960L;
	
	private Parameter parameter;
	
	private Switcher switcher;
	
	public JParmPanel(Parameter parameter, Switcher switcher) {
		this.switcher = switcher;
		this.parameter = parameter;
	}

	public Parameter getParameter() {
		return parameter;
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if(switcher.getParameterIndex() == parameter.getID()) {
			parameter.paint(g, new Color(Parameter.RGB_SELECTED));
		} else {
			parameter.paint(g, new Color(Parameter.RGB_NO_SELECTED));
		}
	}
}
