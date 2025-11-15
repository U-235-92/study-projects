package aq.cockroach.core;

public class EnterException extends Exception {
	
	private static final long serialVersionUID = 9143856183366908203L;

	@Override
	public String toString() {
		
		return "EnterException: invalid data!";
	}
}
