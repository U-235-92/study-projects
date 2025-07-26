package aq.project.configs;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.context.annotation.PropertySource;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@Configuration
@EnableAspectJAutoProxy
@ComponentScan(basePackages = {"aq.project.aspects", "aq.project.security"})
@PropertySource(value = { "file:src/main/resources/endpoints-names.properties" })
public class CommonConfig {
	
	@Bean
	Validator validator() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		return validator;
	}
}
