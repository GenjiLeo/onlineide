package edu.tum.ase.monolith;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.web.client.RestTemplate;
import org.springframework.boot.web.client.RestTemplateBuilder;

/*
	Create new Spring app
	Remove dependencies that are unnecessary
	Set Ports

	BE: Refactor controller to be RestCotnroller for json api
	FE:
		- Replace calls to DB with request to BE
		- Remove parts from FE - DB

 */
@SpringBootApplication
public class MonolithApplication {

	public static void main(String[] args) {
		SpringApplication.run(MonolithApplication.class, args);
	}

	@Bean
	public RestTemplate restTemplate(RestTemplateBuilder builder){
		return builder.build();
	}
}
