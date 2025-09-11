package aq.project.service;

import java.io.IOException;
import java.time.Duration;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;
import java.util.logging.Formatter;
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
import org.apache.kafka.common.TopicPartition;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper;

import aq.project.dto.ManufactureRequestDto;
import aq.project.dto.ProductDto;
import aq.project.mapper.ManufactureRequestMapper;
import aq.project.mapper.ProductMapper;
import aq.project.model.ManufactureRequest;
import aq.project.model.Product;
import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validator;

@Service
public class ConsumerService {

	private static final String COMPLETE_PRODUCTS_TOPIC = "complete-products";
	private static final String MANUFACTURE_REQUESTS_TOPIC = "manufacture-requests";
	
	private static final Logger INFO_LOGGER = Logger.getLogger(ConsumerService.class.getName() + "-INFO");
	private static final Logger WARN_LOGGER = Logger.getLogger(ConsumerService.class.getName() + "-WARN");
	
	private Executor executor;
	
	@Autowired
	private ProductMapper productMapper;
	@Autowired
	private ManufactureRequestMapper manufactureRequestMapper;
	
	@Autowired
	private Validator validator;
	
	@Autowired
	private Consumer<String, String> consumer;
	@Autowired
	private Producer<Long, ManufactureRequestDto> producer;
	
	public ConsumerService() {
		super();
		setUpLoggers();
		executor = Executors.newCachedThreadPool();
	}

	private static void setUpLoggers() {
		WARN_LOGGER.setLevel(Level.WARNING);
		WARN_LOGGER.setUseParentHandlers(false);
		
		INFO_LOGGER.setLevel(Level.INFO);
		INFO_LOGGER.setUseParentHandlers(false);
		Formatter formatter = new SimpleFormatter() {
			@Override
			public String format(LogRecord record) {
				return String.format("%s: %s%n", record.getLevel().getName(), record.getMessage());
			}
		};
		Handler infoHandler = new StreamHandler(System.out, formatter);
		INFO_LOGGER.addHandler(infoHandler);
	}
	
	public void sendProduceRequest(ManufactureRequest request) {
		checkViolations(validator.validate(request));
		ManufactureRequestDto dto = manufactureRequestMapper.toProduceRequestDto(request);
		ProducerRecord<Long, ManufactureRequestDto> producerRecord = new ProducerRecord<>(MANUFACTURE_REQUESTS_TOPIC, dto);
		producer.send(producerRecord, (meta, exc) -> {
			if(exc != null) 
				WARN_LOGGER.warning(exc.getMessage());
		});
		INFO_LOGGER.info("Consumer sent manufacture request: " + dto.toString());
	}
	
	public void recieveProducts() {
		executor.execute(() -> {
			ObjectMapper objectMapper = new ObjectMapper();
			consumer.subscribe(Collections.singletonList(COMPLETE_PRODUCTS_TOPIC));
			while(true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(250));
				records.forEach(record -> {
					try {						
						ProductDto dto = objectMapper.readValue(record.value(), ProductDto.class);
						checkViolations(validator.validate(dto));
						Product product = productMapper.toProduct(dto);
						INFO_LOGGER.info(String.format("Recieved product: [id: %s, name: %s, color: %s, created_at: %s ] #%d", 
								product.getId(), 
								product.getName(), 
								product.getColor(), 
								product.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), 
								record.offset()));
					} catch(IOException ex) {
						ex.printStackTrace();
					}
				});
			}
		});
	}
	
	public void recieveProductsFromBeginning() {
		executor.execute(() -> {
			ObjectMapper objectMapper = new ObjectMapper();
			List<TopicPartition> topicPartitions = consumer.partitionsFor(COMPLETE_PRODUCTS_TOPIC)
					.stream()
					.map(partition -> new TopicPartition(COMPLETE_PRODUCTS_TOPIC, partition.partition()))
					.toList();
			consumer.assign(topicPartitions);
			topicPartitions.forEach(partition -> consumer.seek(partition, 0));
			while(true) {
				ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(250));
				records.forEach(record -> {
					try {						
						ProductDto dto = objectMapper.readValue(record.value(), ProductDto.class);
						checkViolations(validator.validate(dto));
						Product product = productMapper.toProduct(dto);
						INFO_LOGGER.info(String.format("Recieved product: [id: %s, name: %s, color: %s, created_at: %s ] #%d", 
								product.getId(), 
								product.getName(), 
								product.getColor(), 
								product.getCreatedAt().format(DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss")), 
								record.offset()));
					} catch(IOException ex) {
						ex.printStackTrace();
					}
				});
			}
		});
	}
	
	private <T> void checkViolations(Set<ConstraintViolation<T>> violations) {
		if(!violations.isEmpty())
			violations.forEach(violation -> WARN_LOGGER.info(String.format("[ %s ] %s: %s", 
					violation.getMessage(), 
					violation.getPropertyPath(), 
					violation.getInvalidValue())));
	}
}
