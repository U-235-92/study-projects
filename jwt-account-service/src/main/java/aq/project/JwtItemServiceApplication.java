package aq.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;

@SpringBootApplication
@EnableAspectJAutoProxy
public class JwtItemServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(JwtItemServiceApplication.class, args);
	}

	@Bean
	Validator validator() {
		ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();
		Validator validator = validatorFactory.getValidator();
		return validator;
	}
}
