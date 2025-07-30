package aq.project.utils;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.stereotype.Component;

import aq.project.exceptions.UnknownEndPointException;

@Component
public class EndpointNameHolder {

	private Properties properties;
	
	public EndpointNameHolder() throws FileNotFoundException, IOException {
		properties = new Properties();
		String file = "src/main/resources/endpoints-names.properties";
		properties.load(new BufferedInputStream(new FileInputStream(new File(file))));
	}
	
	public String getEndpoint(String endPoint) {
		String endpoint = properties.getProperty(endPoint);
		if(endpoint == null)
			throw new UnknownEndPointException(endPoint);
		return endpoint;
	}
}
