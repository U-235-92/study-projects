package jp.tam.util;

import java.util.ArrayList;
import java.util.List;

import jp.tam.swing.JParmPanel;

public class ParameterStore {

	private List<JParmPanel> parameterList;
	
	public ParameterStore() {
		parameterList = new ArrayList<>();
	}
	
	public List<JParmPanel> getParameterList() {
		return parameterList;
	}
	
	public void add(JParmPanel jParmPNL) {
		jParmPNL.getParameter().setID();
		parameterList.add(jParmPNL);
	}
}
