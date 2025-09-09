package aq.project;

import aq.project.service.LogService;

public class Main {

	public static void main(String[] args) {
		LogService logService = new LogService();
		logService.printLog();
	}
}
