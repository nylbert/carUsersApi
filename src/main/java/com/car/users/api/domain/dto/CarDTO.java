package com.car.users.api.domain.dto;

import lombok.Data;

@Data
public class CarDTO {
	
	private Integer id;
	private Integer year;
	private String licensePlate;
	private String model;
	private String color;
	
}
