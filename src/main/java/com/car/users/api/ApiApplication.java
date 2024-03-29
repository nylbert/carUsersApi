package com.car.users.api;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

import lombok.Generated;

@SpringBootApplication
@EnableScheduling
public class ApiApplication {

	@Generated
	public static void main(String[] args) {
		SpringApplication.run(ApiApplication.class, args);
	}

}
