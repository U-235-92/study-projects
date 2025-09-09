package aq.project.service;

import java.time.Duration;
import java.util.Collections;
import java.util.Properties;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

public class LogService {

	private Consumer<String, String> consumer;
	
	public LogService() {
		Properties properties = new Properties();
		properties.put(ConsumerConfig.ENABLE_AUTO_COMMIT_CONFIG, "true");
		properties.put(ConsumerConfig.GROUP_ID_CONFIG, "log_consumers");
		properties.put(ConsumerConfig.AUTO_COMMIT_INTERVAL_MS_CONFIG, "3000");
		properties.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:5050,localhost:5051,localhost:5052");
		properties.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");
		properties.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		properties.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
		consumer = new KafkaConsumer<String, String>(properties);
	}
	
	public void printLog() {
		consumer.subscribe(Collections.singleton("logs"));
		while(true) {
			ConsumerRecords<String, String> records = consumer.poll(Duration.ofMillis(250));
			records.forEach(record -> System.out.println(record.value()));
		}
	}
}
