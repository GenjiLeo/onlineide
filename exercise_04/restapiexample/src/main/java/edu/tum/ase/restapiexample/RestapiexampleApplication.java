package edu.tum.ase.restapiexample;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// Search for annotations in the classes
// Automatically handle how Bean A uses Bean B i.e. Autowired
@SpringBootApplication
public class RestapiexampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(RestapiexampleApplication.class, args);
	}

}
