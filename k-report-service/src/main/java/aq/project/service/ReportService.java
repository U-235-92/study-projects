package aq.project.service;

import java.time.Duration;
import java.util.Properties;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.kstream.Consumed;
import org.apache.kafka.streams.kstream.TimeWindows;

import aq.project.util.serialize.JsonDeserializer;
import aq.project.util.serialize.JsonSerializer;

public class ReportService {
	
	private Properties properties;
	private Executor executor;
	
	public ReportService() {
		properties = new Properties();
		properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:5050");
		properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "report-service");
		executor = Executors.newFixedThreadPool(3);
	}
	
	public void printTotalProductNumber(String name) {
		executor.execute(() -> {
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream("complete-products", Consumed.with(Serdes.String(), Serdes.serdeFrom(new JsonSerializer(), new JsonDeserializer())))
				.filter((k, v) -> v.getName().equals(name))
				.groupByKey()
				.count()
				.toStream()
				.foreach((k, v) -> System.out.println(k + ": " + v));
			@SuppressWarnings("resource")
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
	
	public void allocateByColor(String color) {
		executor.execute(() -> {			
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream("complete-products", Consumed.with(Serdes.String(), Serdes.serdeFrom(new JsonSerializer(), new JsonDeserializer())))
				.filter((k, v) -> v.getColor().equals(color))
				.selectKey((k, v) -> v.getColor())
				.to("color-products");
			@SuppressWarnings("resource")
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
	
	public void printCountProductNumberByPeriod(long from, Duration duration) {
		executor.execute(() -> {			
			StreamsBuilder streamsBuilder = new StreamsBuilder();
			streamsBuilder.stream("complete-products", Consumed.with(Serdes.String(), Serdes.serdeFrom(new JsonSerializer(), new JsonDeserializer())))
				.filter((k, v) -> v.getCreatedAt() >= from)
				.groupBy((k, v) -> v.getColor())
				.windowedBy(TimeWindows.ofSizeWithNoGrace(duration))
				.count()
				.toStream()
				.foreach((k, v) -> System.out.println(k + ": " + v));
			@SuppressWarnings("resource")
			KafkaStreams kafkaStreams = new KafkaStreams(streamsBuilder.build(), properties);
			kafkaStreams.start();
		});
	}
}
