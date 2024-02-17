package com.car.users.api.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "app_car")
public class Car {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "car_year", length = 4, nullable = false)
	private Integer year;
	
	@Column(name = "license_plate", nullable = false)
	private String licensePlate;
	
	@Column(name = "model", nullable = false)
	private String model;
	
	@Column(name = "color", nullable = false)
	private String color;
	
	@Column(name = "user_id")
    private Long userId;
}
