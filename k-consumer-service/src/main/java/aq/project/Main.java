package aq.project;

import java.time.LocalDateTime;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import aq.project.model.ManufactureRequest;
import aq.project.service.ConsumerService;

public class Main {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(new String[] {
			"aq.project.config",
			"aq.project.mapper",
			"aq.project.service"
		});
		ConsumerService consumerService = applicationContext.getBean(ConsumerService.class);
		ManufactureRequest manufactureRequest = new ManufactureRequest(1, 1000, "BLUE", "ABC-585", LocalDateTime.now());
		consumerService.sendProduceRequest(manufactureRequest);
	}
}
