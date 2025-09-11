package aq.project.service;

import java.time.Duration;
import java.util.Properties;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aq.project.dto.ProductDto;

public class ReportService {
	
	private static final String COLOR_PRODUCTS_TOPIC = "color-products";
	private static final String COMPLETE_PRODUCTS_TOPIC = "complete-products";
	
	private Executor executor;
	private Properties properties;
	private ObjectMapper objectMapper;
	private ValueMapper<String, ProductDto> valueMapper;	
	
	public ReportService() {
		properties = new Properties();
		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:5050");
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "report-service#" + UUID.randomUUID().toString());
		properties.put("auto.offset.reset", "earliest");
		executor = Executors.newCachedThreadPool();
		objectMapper = new ObjectMapper();
		valueMapper = value -> {
			try {
				return objectMapper.readValue(value, ProductDto.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		};
	}
	
	@SuppressWarnings("resource")
	public  void printProductsAsStream() {
		executor.execute(() -> {
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream(COMPLETE_PRODUCTS_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
				.mapValues(v -> valueMapper.apply(v))
				.filter((k, v) -> v != null)
				.foreach((k, v) -> System.out.println(v));
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
	
	@SuppressWarnings("resource")
	public void printTotalProductNumber(String name) {
		executor.execute(() -> {
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream(COMPLETE_PRODUCTS_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
				.mapValues(v -> valueMapper.apply(v))
				.filter((k, v) -> v != null && v.getName().equals(name))
				.peek((key, value) -> System.out.println(key))
				.groupByKey()
				.count()
				.toStream()
				.foreach((k, v) -> System.out.println(k + ": " + v));
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
	
	@SuppressWarnings("resource")
	public void allocateByColor(String color) {
		executor.execute(() -> {			
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream(COMPLETE_PRODUCTS_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
				.mapValues(v -> valueMapper.apply(v))
				.filter((k, v) ->  v != null && v.getColor().equals(color))
				.selectKey((k, v) -> v.getColor())
				.to(COLOR_PRODUCTS_TOPIC);
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
	
	@SuppressWarnings("resource")
	public void printCountProductNumberByPeriod(long from, Duration duration) {
		executor.execute(() -> {			
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream(COMPLETE_PRODUCTS_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
				.mapValues(v -> valueMapper.apply(v))
				.filter((k, v) -> v != null && v.getCreatedAt() >= from)
				.groupBy((k, v) -> v.getColor())
				.windowedBy(TimeWindows.ofSizeWithNoGrace(duration))
				.count()
				.toStream()
				.foreach((k, v) -> System.out.println(k + ": " + v));
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
}
