package aq.project;

import java.time.LocalDateTime;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import aq.project.model.ManufactureRequest;
import aq.project.service.ConsumerService;

public class ConsumerServiceMain {

	@SuppressWarnings("resource")
	public static void main(String[] args) {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(new String[] {
			"aq.project.config",
			"aq.project.mapper",
			"aq.project.service"
		});
		ConsumerService consumerService = applicationContext.getBean(ConsumerService.class);
		consumerService.recieveProductsFromBeginning();
	}
	
	@SuppressWarnings("unused")
	private static void sendManufactureRequest(ConsumerService consumerService) {
		ManufactureRequest bowRequest = new ManufactureRequest(1, 10, "BLACK", "BOW-5588", LocalDateTime.now());
		ManufactureRequest rdeRequest = new ManufactureRequest(2, 15, "RED", "RDE-8085", LocalDateTime.now());
		ManufactureRequest bhjRequest = new ManufactureRequest(3, 20, "BLUE", "BHJ-5050", LocalDateTime.now());
		consumerService.sendProduceRequest(bowRequest);
		consumerService.sendProduceRequest(rdeRequest);
		consumerService.sendProduceRequest(bhjRequest);		
	}
}
