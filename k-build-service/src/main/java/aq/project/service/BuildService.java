package aq.project.service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import aq.project.dto.ManufactureRequestDto;
import aq.project.dto.ProductDto;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BuildService {

	private final KafkaTemplate<String, ProductDto> kafkaTemplate;
	
	@KafkaListener(topics = "manufacture-requests", groupId = "product-builders")
	public void buildProduct(ManufactureRequestDto requestDto) {
		String party = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "-#" + UUID.randomUUID().toString();
		int count = requestDto.getCount();
		for(int i = 0; i < count; i++) {
			String id = UUID.randomUUID().toString();
			long createdAt = System.currentTimeMillis();
			String color = requestDto.getColor();
			String name = requestDto.getProduct();
			ProductDto productDto = new ProductDto(id, party, color, name, createdAt);
			randomSleep();
			kafkaTemplate.send("complete-products", productDto);
		}
	}
	
	private void randomSleep() {
		try {
			TimeUnit.MILLISECONDS.sleep(100 + (int) (Math.random() * (2000 - 100) + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
