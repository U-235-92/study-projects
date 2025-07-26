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
public class InfoMessageHolder {

	private Properties properties;
	
	public InfoMessageHolder() throws FileNotFoundException, IOException {
		properties = new Properties();
		String file = "src/main/resources/info-messages.properties";
		properties.load(new BufferedInputStream(new FileInputStream(new File(file))));
	}
	
	public String getMessage(String property) {
		String message = properties.getProperty(property);
		if(message == null)
			throw new UnknownPropertyException(String.format("Unknown info message property: %s", property));
		return message;
	}
}
