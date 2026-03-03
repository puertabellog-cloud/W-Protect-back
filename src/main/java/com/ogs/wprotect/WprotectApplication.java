package com.ogs.wprotect;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class WprotectApplication {

	public static void main(String[] args) {
		SpringApplication.run(WprotectApplication.class, args);
	}

}
