package aq.project.util.serialize;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aq.project.dto.ProductDto;

public class JsonSerializer implements Serializer<ProductDto> {

	@Override
	public byte[] serialize(String topic, ProductDto dto) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			byte[] bytes = mapper.writer().writeValueAsBytes(dto);
			return bytes;
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}
}
