package aq.project.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

import aq.project.exceptions.UnknownPropertyException;

@Component
public class EndpointNameHolder {

	private Properties properties;
	
	public EndpointNameHolder() throws FileNotFoundException, IOException {
		properties = new Properties();
		String file = "src/main/resources/endpoints-names.properties";
		properties.load(new BufferedInputStream(new FileInputStream(new File(file))));
	}
	
	public String getEndpoint(String property) {
		String endpoint = properties.getProperty(property);
		if(endpoint == null)
			throw new UnknownPropertyException(String.format("Unknown end-point property: %s", property));
		return endpoint;
	}
}
