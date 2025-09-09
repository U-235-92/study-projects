package aq.project.util.serialize;

import org.apache.kafka.common.serialization.Serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aq.project.dto.ManufactureRequestDto;

public class JsonSerializer implements Serializer<ManufactureRequestDto> {

	@Override
	public byte[] serialize(String topic, ManufactureRequestDto data) {
		ObjectMapper mapper = new ObjectMapper();
		try {
			byte[] bytes = mapper.writer().writeValueAsBytes(data);
			return bytes;
		} catch (JsonProcessingException e) {
			throw new IllegalStateException(e);
		}
	}
}
