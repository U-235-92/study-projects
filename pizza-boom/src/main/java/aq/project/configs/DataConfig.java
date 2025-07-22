package aq.project.configs;

import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = {"aq.project.entities"})
public class DataConfig {

	@Bean
	@SuppressWarnings("unused")
	ApplicationRunner preStartApplicationDataLoad() {
		return args -> {
			
		};
	}
}
