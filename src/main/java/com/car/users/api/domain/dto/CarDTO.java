package com.car.users.api.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class CarDTO {
	
	private Integer id;
	private Integer year;
	private String licensePlate;
	private String model;
	private String color;
	private byte[] image;
	
}
