package com.car.users.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.mapper.CarMapper;
import com.car.users.api.domain.model.Car;
import com.car.users.api.repository.CarRepository;

@Service
public class CarService implements ICarService {
	
	private CarRepository carRepository;
	
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}
	
	@Override
	public List<Car> insert(List<CarDTO> carsDTO, Integer userId) {
		List<Car> cars = new ArrayList<>();
		carsDTO.forEach(car -> cars.add(insert(car, userId)));
		return cars;
	}
	
	@Override
	public Car insert(CarDTO carDTO, Integer userId) {
		Car car = CarMapper.INSTANCE.carDtoToCar(carDTO, userId);
		return insert(car);
	}
	
	@Override
	public Car insert(Car car) {
		return this.carRepository.save(car);
	}
	
	@Override
	public List<Car> findCarsByUserId(Integer userId) {
		return this.carRepository.findByUserId(userId);
	}
	
	@Override
	public List<CarDTO> findCarsDTOByUserId(Integer userId) {
		return CarMapper.INSTANCE.carToCarDto(this.carRepository.findByUserId(userId));
	}
	
	@Override
	public void deleteByUserId(Integer userId) {
		List<Car> cars = findCarsByUserId(userId);
		cars.forEach(car -> this.carRepository.delete(car));
	}
	
}
