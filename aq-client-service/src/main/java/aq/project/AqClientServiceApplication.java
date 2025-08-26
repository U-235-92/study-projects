package aq.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class AqClientServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AqClientServiceApplication.class, args);
	}

}
