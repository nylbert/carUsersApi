package com.car.users.api.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.car.users.api.constant.CarConstants;
import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.mapper.CarMapper;
import com.car.users.api.domain.model.Car;
import com.car.users.api.infra.exception.DuplicatedFieldException;
import com.car.users.api.infra.exception.InvalidFieldException;
import com.car.users.api.infra.exception.RequiredFieldException;
import com.car.users.api.repository.CarRepository;
import com.car.users.api.util.CarUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class CarService implements ICarService {
	
	private CarRepository carRepository;
	
	public CarService(CarRepository carRepository) {
		this.carRepository = carRepository;
	}
	
	@Override
	public CarDTO insert(CarDTO carDTO, Integer userId) {
		validateRequiredFields(carDTO);
		validateInvalidFields(carDTO);
		validateUniqueness(carDTO);
		
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
		validateRequiredFields(carDTO);
		validateInvalidFields(carDTO);
		validateUniqueness(id, carDTO);
		
		Car car = this.carRepository.findByIdAndUserId(id, userId);
		CarMapper.INSTANCE.carDtoToCar(carDTO, car);
		return CarMapper.INSTANCE.carToCarDto(this.carRepository.save(car));
	}
	
	private void validateUniqueness(CarDTO carDTO) {
		validateUniqueness(null, carDTO);
	}
	
	private void validateUniqueness(Integer id, CarDTO carDTO) {
		Long countLicensePlate = id == null ? this.carRepository.countByLicensePlate(carDTO.getLicensePlate())
				: this.carRepository.countByLicensePlateAndIdNot(carDTO.getLicensePlate(), id);
		boolean licensePlateExists = countLicensePlate > 0;

		if (licensePlateExists) {
			throw new DuplicatedFieldException(CarConstants.LICENSE_PLATE);
		}
	}
	
	private void validateRequiredFields(CarDTO carDTO) {
		List<String> fields = new ArrayList<>();

		if (carDTO.getYear() == null) {
			fields.add(CarConstants.YEAR);
		}
		if (StringUtils.isBlank(carDTO.getModel())) {
			fields.add(CarConstants.MODEL);
		}
		if (StringUtils.isBlank(carDTO.getColor())) {
			fields.add(CarConstants.COLOR);
		}
		if (StringUtils.isBlank(carDTO.getLicensePlate())) {
			fields.add(CarConstants.LICENSE_PLATE);
		}

		if (fields.size() > 0) {
			throw new RequiredFieldException(fields);
		}
	}
	
	private void validateInvalidFields(CarDTO carDTO) {
		List<String> fields = new ArrayList<>();

		if (!CarUtils.isValidLicensePlate(carDTO.getLicensePlate())) {
			fields.add(CarConstants.LICENSE_PLATE);
		}

		if (!CarUtils.isValidYear(carDTO.getYear())) {
			fields.add(CarConstants.YEAR);
		}

		if (fields.size() > 0) {
			throw new InvalidFieldException(fields);
		}
	}
}
