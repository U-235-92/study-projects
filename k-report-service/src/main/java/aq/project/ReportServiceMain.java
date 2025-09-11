package aq.project;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import aq.project.service.ReportService;

public class ReportServiceMain {

	public static void main(String[] args) {
		ReportService reportService = new ReportService();
//		reportService.printProductsAsStream();
//		allocateProductsByColor(reportService);
//		reportService.printCountProductNumberByPeriod(getFromMills(), getDuration());
		reportService.printTotalProductNumber("BOW-5588");
//		reportService.printTotalProductNumber("RDE-8085");
//		reportService.printTotalProductNumber("BHJ-5050");
	}
	
	@SuppressWarnings("unused")
	private static void allocateProductsByColor(ReportService reportService) {
		reportService.allocateByColor("RED");
		reportService.allocateByColor("BLUE");
		reportService.allocateByColor("BLACK");
	}
	
	@SuppressWarnings("unused")
	private static long getFromMills() {
		return LocalDateTime.now()
				.minusDays(1)
				.atZone(ZoneId.systemDefault())
				.toInstant()
				.toEpochMilli();
	}
	
	@SuppressWarnings("unused")
	private static Duration getDuration() {
		return Duration.ofHours(1);
	}
}
