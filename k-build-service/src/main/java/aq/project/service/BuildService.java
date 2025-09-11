package aq.project.service;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import aq.project.dto.ManufactureRequestDto;
import aq.project.dto.ProductDto;

@Service
public class BuildService {

	private static final Logger LOGGER = Logger.getLogger(BuildService.class.getName());
	
	@Autowired
	private KafkaTemplate<String, String> kafkaTemplate;
	
	public BuildService() {
		super();
		setUpLogger();
	}
	
	private static void setUpLogger() {
		Formatter formatter = new SimpleFormatter() {
			@Override
			public String format(LogRecord record) {
				return String.format("BuildService: [%s] - %s%n", record.getLevel().getName(), record.getMessage());
			}
		};
		Handler handler = new StreamHandler(System.out, formatter);
		LOGGER.addHandler(handler);
		LOGGER.setLevel(Level.INFO);
		LOGGER.setUseParentHandlers(false);
	}
	
	@KafkaListener(topics = "manufacture-requests", groupId = "product-builders")
	public void buildProduct(String rawJson) throws IOException {
		LOGGER.info(String.format("Recieved: %s, Current thread: %s", rawJson, Thread.currentThread().getName()));
		ObjectMapper objectMapper = new ObjectMapper();
		ManufactureRequestDto requestDto = objectMapper.readValue(rawJson, ManufactureRequestDto.class);
		String party = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "-#" + UUID.randomUUID().toString();
		int count = requestDto.getCount();
		for(int i = 0; i < count; i++) {
			String id = UUID.randomUUID().toString();
			long createdAt = System.currentTimeMillis();
			String color = requestDto.getColor();
			String name = requestDto.getProduct();
			ProductDto productDto = new ProductDto(id, party, color, name, createdAt);
			String outJson = objectMapper.writeValueAsString(productDto);
			randomSleep();
			kafkaTemplate.send("complete-products", name, outJson);
		}
	}
	
	private void randomSleep() {
		try {
			TimeUnit.MILLISECONDS.sleep(100 + (int) (Math.random() * (500 - 100) + 1));
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
