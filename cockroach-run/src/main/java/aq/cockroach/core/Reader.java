package aq.cockroach.core;

import javax.swing.JTextField;

public class Reader {

	public int readTextField(JTextField jTextFLD) throws EnterException {
		if(!(Integer.valueOf(jTextFLD.getText()) instanceof Integer)) {
			throw new EnterException();
		} else {
			return Integer.valueOf(jTextFLD.getText());
		}
	}
}
