package aq.project;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.ulisesbocchio.jasyptspringboot.annotation.EnableEncryptableProperties;

@SpringBootApplication
@EnableEncryptableProperties
public class AqResourceServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AqResourceServiceApplication.class, args);
	}
}
