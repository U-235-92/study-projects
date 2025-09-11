package aq.project;

import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;

import aq.project.service.ReportService;

public class ReportServiceMain {

	public static void main(String[] args) {
		ReportService reportService = new ReportService();
		reportService.printProductsAsStream();
//		allocateProductsByColor(reportService, "BLACK");
//		reportService.calculateCountProductNumberByPeriod(getFromMills(), getDuration());
//		reportService.calculateTotalProductNumber("BOW-5588");
//		reportService.calculateTotalProductNumber("RDE-8085");
//		reportService.calculateTotalProductNumber("BHJ-5050");
//		reportService.calculateTotalProductNumber("YLW-5008");
	}
	
	@SuppressWarnings("unused")
	private static void allocateProductsByColor(ReportService reportService, String color) {
		reportService.allocateByColor(color.toUpperCase());
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
