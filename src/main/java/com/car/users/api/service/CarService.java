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
	public List<Car> find(Integer userId) {
		return this.carRepository.findByUserId(userId);
	}
	
	@Override
	public Car find(Integer id, Integer userId) {
		return this.carRepository.findByIdAndUserId(id, userId);
	}
	
	@Override
	public void delete(Integer userId) {
		List<Car> cars = find(userId);
		cars.forEach(car -> this.carRepository.delete(car));
	}
	
	@Override
	public void delete(Integer id, Integer userId) {
		Car car = find(id, userId);
		this.carRepository.delete(car);
	}
	
	@Override
	public Car update(Integer id, Integer userId, CarDTO carDTO) {
		Car car = find(id, userId);
		CarMapper.INSTANCE.carDtoToCar(carDTO, car);
		return this.carRepository.save(car);
	}
}
