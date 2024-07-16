package com.example.vebibeer_be;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class VebibeerBeApplication {

	public static void main(String[] args) {
		SpringApplication.run(VebibeerBeApplication.class, args);
	}

}
