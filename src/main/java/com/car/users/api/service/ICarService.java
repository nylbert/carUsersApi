package com.car.users.api.service;

import java.util.List;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.model.Car;

public interface ICarService {

	List<Car> insert(List<CarDTO> cars, Integer userId);

	Car insert(CarDTO carDTO, Integer userId);

	Car insert(Car car);

	List<Car> findCarsByUserId(Integer userId);

	void deleteByUserId(Integer userId);

	List<CarDTO> findCarsDTOByUserId(Integer userId);

}
