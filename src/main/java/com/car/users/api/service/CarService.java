package com.car.users.api.service;

import org.springframework.stereotype.Service;

import com.car.users.api.repository.CarRepository;

@Service
public class CarService implements ICarService {
	
	private CarRepository carRepository;
	
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}
	
}
