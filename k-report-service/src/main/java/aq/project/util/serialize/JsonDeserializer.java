package aq.project.util.serialize;

import java.io.IOException;

import org.apache.kafka.common.serialization.Deserializer;

import com.fasterxml.jackson.core.exc.StreamReadException;
import com.fasterxml.jackson.databind.DatabindException;
import com.fasterxml.jackson.databind.ObjectMapper;

import aq.project.dto.ProductDto;

public class JsonDeserializer implements Deserializer<ProductDto> {

	@Override
	public ProductDto deserialize(String topic, byte[] data) {
		try {
			ObjectMapper mapper = new ObjectMapper();
			ProductDto productDto = mapper.readValue(data, ProductDto.class);
			return productDto;
		} catch (StreamReadException e) {
			throw npe(e);
		} catch (DatabindException e) {
			throw npe(e);
		} catch (IOException e) {
			throw npe(e);
		}
	}
	
	private NullPointerException npe(Exception cause) {
		NullPointerException exc = new NullPointerException();
		exc.initCause(cause);
		return exc;
	}
}
