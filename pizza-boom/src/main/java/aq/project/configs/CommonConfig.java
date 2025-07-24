package aq.project.configs;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.Profile;

import aq.project.repositories.UserAuthorityRepository;
import aq.project.utils.UserAuthorityHolder;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"aq.project.aspects", "aq.project.security"})
public class CommonConfig {

	@Profile("prod")
	@Bean(value = "customExceptionMessagesHolder")
	Properties exceptionMessagesHolder() throws FileNotFoundException, IOException {
		String file = "src/main/resources/exception-messages.properties";
		Properties properties = new Properties();
		properties.load(new BufferedInputStream(new FileInputStream(new File(file))));
		return properties;
	}
	
	@Profile("prod")
	@Bean(value = "infoMessagesHolder")
	Properties infoMessagesHolder() throws FileNotFoundException, IOException {
		String file = "src/main/resources/info-messages.properties";
		Properties properties = new Properties();
		properties.load(new BufferedInputStream(new FileInputStream(new File(file))));
		return properties;
	}
	
	@Bean
	@Profile("prod")
	UserAuthorityHolder userAuthorityHolder(UserAuthorityRepository userAuthorityRepository, @Qualifier("customExceptionMessagesHolder") Properties exceptionMessagesHolder) {
		return new UserAuthorityHolder(userAuthorityRepository, exceptionMessagesHolder);
	}
}
