package com.car.users.api.service;

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
	public CarDTO insert(CarDTO carDTO, Integer userId) {
		Car car = CarMapper.INSTANCE.carDtoToCar(carDTO, userId);
		return CarMapper.INSTANCE.carToCarDto(this.carRepository.save(car));
	}
	
	@Override
	public List<CarDTO> find(Integer userId) {
		return CarMapper.INSTANCE.carToCarDto(this.carRepository.findByUserId(userId));
	}
	
	@Override
	public CarDTO find(Integer id, Integer userId) {
		return CarMapper.INSTANCE.carToCarDto(this.carRepository.findByIdAndUserId(id, userId));
	}
	
	@Override
	public void delete(Integer id, Integer userId) {
		Car car = this.carRepository.findByIdAndUserId(id, userId);
		this.carRepository.delete(car);
	}
	
	@Override
	public CarDTO update(Integer id, Integer userId, CarDTO carDTO) {
		Car car = this.carRepository.findByIdAndUserId(id, userId);
		CarMapper.INSTANCE.carDtoToCar(carDTO, car);
		return CarMapper.INSTANCE.carToCarDto(this.carRepository.save(car));
	}
}
