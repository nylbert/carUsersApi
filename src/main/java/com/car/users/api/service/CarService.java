package com.car.users.api.service;

import org.springframework.stereotype.Service;

import com.car.users.api.dto.CarDTO;
import com.car.users.api.mapper.CarMapper;
import com.car.users.api.model.Car;
import com.car.users.api.repository.CarRepository;

@Service
public class CarService implements ICarService {
	
	private CarRepository carRepository;
	
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}
	
	public Car insert(CarDTO carDTO) {
		Car car = CarMapper.INSTANCE.carDtoToCar(carDTO);
		return this.carRepository.save(car);
	}
	
}
