package edu.tum.ase.darkmode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@SpringBootApplication
@RestController
@EnableEurekaClient
public class DarkmodeApplication {

	private boolean enabled = false;
	private LocalDateTime lastSet = LocalDateTime.MIN;

	public static void main(String[] args) {
		SpringApplication.run(DarkmodeApplication.class, args);
	}

	@RequestMapping(path = "/dark-mode/toggle", method = RequestMethod.GET)
	public void toggleDarkMode() {
		// Ignore toggle requests if the last one was less than three seconds
		// ago (solves problem of multiple requests being sent by the MC for one
		// button press)
		if (LocalDateTime.now().minusSeconds(3).isBefore(lastSet))
			return;
		// Save the time when it was last toggled
		lastSet = LocalDateTime.now();
		enabled = !enabled;
	}

	@RequestMapping(path = "/dark-mode", method = RequestMethod.GET)
	public boolean outputDarkMode() {
		return enabled;
	}
}
