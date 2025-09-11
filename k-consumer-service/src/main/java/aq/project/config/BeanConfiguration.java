package aq.project.config;

import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import aq.project.dto.ManufactureRequestDto;
import aq.project.util.serialize.JsonSerializer;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Configuration
public class BeanConfiguration {

	private static final String BOOTSTRAP_SERVERS = "localhost:5050";
	
	@Bean
	public Consumer<String, String> consumer() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "product_consumers");
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "3000");
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.HEARTBEAT_INTERVAL_MS_CONFIG, "5000");
		properties.put(ConsumerConfig.MAX_POLL_INTERVAL_MS_CONFIG, "10000");
		properties.put(ConsumerConfig.SESSION_TIMEOUT_MS_CONFIG, "20000");
		Consumer<String, String> consumer = new KafkaConsumer<>(properties);
		return consumer;
	}
	
	@Bean
	public Producer<Long, ManufactureRequestDto> producer() {
		Properties properties = new Properties();
		properties.put(ProducerConfig.ACKS_CONFIG, "-1");
		properties.put(ProducerConfig.RETRIES_CONFIG, "3");
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVERS);
		properties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class);
		properties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
		Producer<Long, ManufactureRequestDto> producer = new KafkaProducer<>(properties);
		return producer;
	}
	
	@Bean
	public Validator validator() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		return validator;
	}
}
