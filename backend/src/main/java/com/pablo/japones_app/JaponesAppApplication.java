package com.pablo.japones_app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;

@SpringBootApplication
@ConfigurationPropertiesScan
public class  JaponesAppApplication {

	public static void main(String[] args) {
		SpringApplication.run(JaponesAppApplication.class, args);
	}

}

