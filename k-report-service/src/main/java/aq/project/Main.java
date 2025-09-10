package aq.project;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import aq.project.service.ReportService;

public class Main {

	public static void main(String[] args) {
		ReportService reportService = new ReportService();
		reportService.allocateByColor("RED");
		reportService.allocateByColor("BLUE");
		reportService.allocateByColor("BLACK");
		reportService.printCountProductNumberByPeriod(LocalDateTime.now().minusDays(1).atZone(ZoneId.systemDefault()).toInstant().toEpochMilli(), Duration.ofHours(1));
		reportService.printTotalProductNumber("ABC-585");
	}
}
