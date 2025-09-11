package aq.project.service;

import java.time.Duration;
import java.util.Optional;
import java.util.Properties;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.Produced;
import org.apache.kafka.streams.kstream.TimeWindows;
import org.apache.kafka.streams.kstream.ValueMapper;
import org.apache.kafka.streams.processor.StreamPartitioner;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aq.project.dto.ProductDto;

public class ReportService {
	
	private static final String COLOR_PRODUCTS_TOPIC = "color-products";
	private static final String AMOUNT_PRODUCTS_TOPIC = "amount-products";
	private static final String COMPLETE_PRODUCTS_TOPIC = "complete-products";
	private static final String AMOUNT_PRODUCTS_PERIOD_TOPIC = "amount-products-by-period";
	
	private Executor executor;
	private Properties properties;
	private ObjectMapper objectMapper;
	private ValueMapper<ProductDto, String> toStringMapper;
	private ValueMapper<String, ProductDto> toProductDtoMapper;
	
	public ReportService() {
		properties = new Properties();
		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:5050");
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "report-service#" + UUID.randomUUID().toString());
		properties.put("auto.offset.reset", "earliest");
		executor = Executors.newCachedThreadPool();
		objectMapper = new ObjectMapper();
		toProductDtoMapper = value -> {
			try {
				return objectMapper.readValue(value, ProductDto.class);
			} catch (JsonMappingException e) {
				e.printStackTrace();
			} catch (JsonProcessingException e) {
				e.printStackTrace();
			}
			return null;
		};
		toStringMapper = value -> {
			try {
				return objectMapper.writeValueAsString(value);
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
				.mapValues(v -> toProductDtoMapper.apply(v))
				.filter((k, v) -> v != null)
				.foreach((k, v) -> System.out.println(k + ": " + v));
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
	
	@SuppressWarnings("resource")
	public void calculateTotalProductNumber(String name) {
		executor.execute(() -> {
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream(COMPLETE_PRODUCTS_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
				.mapValues(v -> toProductDtoMapper.apply(v))
				.filter((k, v) -> v != null && v.getName().equals(name))
				.groupByKey()
				.count()
				.toStream()
				.to(AMOUNT_PRODUCTS_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
	
	@SuppressWarnings("resource")
	public void allocateByColor(String color) {
		executor.execute(() -> {			
			StreamPartitioner<String, String> partitioner = (topic, key, value, numPartitions) -> {
				if(key.equals("RED"))
					return Optional.of(Set.of(0));
				else if(key.equals("BLUE"))
					return Optional.of(Set.of(1));
				else if(key.equals("BLACK"))
					return Optional.of(Set.of(2));
				else
					return Optional.of(Set.of((int) (Math.random() * numPartitions)));
			};
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream(COMPLETE_PRODUCTS_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
				.mapValues(v -> toProductDtoMapper.apply(v))
				.filter((k, v) ->  v != null && v.getColor().equals(color))
				.selectKey((k, v) -> v.getColor())
				.mapValues(toStringMapper)
				.filter((k, v) -> v != null)
				.to(COLOR_PRODUCTS_TOPIC, Produced.with(Serdes.String(), Serdes.String(), partitioner));
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
	
	@SuppressWarnings("resource")
	public void calculateCountProductNumberByPeriod(long from, Duration duration) {
		executor.execute(() -> {			
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream(COMPLETE_PRODUCTS_TOPIC, Consumed.with(Serdes.String(), Serdes.String()))
				.mapValues(v -> toProductDtoMapper.apply(v))
				.filter((k, v) -> v != null && v.getCreatedAt() >= from)
				.groupBy((k, v) -> v.getColor())
				.windowedBy(TimeWindows.ofSizeWithNoGrace(duration))
				.count()
				.toStream()
				.selectKey((k, v) -> k.key())
				.to(AMOUNT_PRODUCTS_PERIOD_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
}
