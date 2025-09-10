package aq.project.service;

import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.logging.Handler;
import java.util.logging.Level;
import java.util.logging.LogRecord;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;
import java.util.logging.StreamHandler;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.stereotype.Service;

import aq.project.dto.ManufactureRequestDto;
import aq.project.dto.ProductDto;
import aq.project.mapper.ManufactureRequestMapper;
import aq.project.mapper.ProductMapper;
import aq.project.model.ManufactureRequest;
import aq.project.model.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ConsumerService {

	private static final Logger INFO_LOGGER = Logger.getLogger(ConsumerService.class.getName() + "-INFO");
	private static final Logger WARN_LOGGER = Logger.getLogger(ConsumerService.class.getName() + "-WARN");
	
	static {
		WARN_LOGGER.setLevel(Level.WARNING);
		WARN_LOGGER.setUseParentHandlers(false);
		
		INFO_LOGGER.setLevel(Level.INFO);
		INFO_LOGGER.setUseParentHandlers(false);
		Handler infoHandler = new StreamHandler(System.out, new SimpleFormatter() {
			@Override
			public String format(LogRecord record) {
				return String.format("%s: %s", record.getLevel().getName(), record.getMessage());
			}
		});
		INFO_LOGGER.addHandler(infoHandler);
	}
	
	private final ProductMapper productMapper;
	private final ManufactureRequestMapper manufactureRequestMapper;
	
	private final Validator validator;
	
	private final Consumer<String, ProductDto> consumer;
	private final Producer<Long, ManufactureRequestDto> producer;
	
	public void sendProduceRequest(ManufactureRequest request) {
		checkViolations(validator.validate(request));
		ManufactureRequestDto dto = manufactureRequestMapper.toProduceRequestDto(request);
		ProducerRecord<Long, ManufactureRequestDto> producerRecord = new ProducerRecord<>("manufacture-requests", dto);
		producer.send(producerRecord, (meta, exc) -> {
			if(exc != null) 
				WARN_LOGGER.warning(exc.getMessage());
		});
	}
	
	public void recieveProducts() {
		ExecutorService executor = Executors.newSingleThreadExecutor();
		executor.submit(() -> {
			consumer.subscribe(Collections.singletonList("complete-products"));
			while(true) {
				ConsumerRecords<String, ProductDto> records = consumer.poll(Duration.ofMillis(250));
				records.forEach(record -> {
					ProductDto dto = record.value();
					checkViolations(validator.validate(dto));
					Product product = productMapper.toProduct(dto);
					INFO_LOGGER.info(String.format("Product recieved: [id: %s, name: %s, color: %s, created_at: %s ]", product.getId(), product.getName(), product.getColor(), product.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss"))));
				});
			}
		});
		executor.shutdown();
	}
	
	private <T> void checkViolations(Set<ConstraintViolation<T>> violations) {
		if(!violations.isEmpty())
			violations.forEach(violation -> WARN_LOGGER.info(String.format("[ %s ] %s: %s", violation.getMessage(), violation.getPropertyPath(), violation.getInvalidValue())));
	}
}
