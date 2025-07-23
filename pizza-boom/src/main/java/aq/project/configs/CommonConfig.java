package aq.project.configs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"aq.project.aspects"})
public class CommonConfig {

	@Bean(value = "customExceptionMessages")
	Properties exceptionMessages() throws FileNotFoundException, IOException {
		String file = "src/main/resources/exception-messages.properties";
		Properties properties = new Properties();
		properties.load(new BufferedInputStream(new FileInputStream(new File(file))));
		return properties;
	}
	
	@Bean(value = "infoMessages")
	Properties infoMessages() throws FileNotFoundException, IOException {
		String file = "src/main/resources/info-messages.properties";
		Properties properties = new Properties();
		properties.load(new BufferedInputStream(new FileInputStream(new File(file))));
		return properties;
	}
}
