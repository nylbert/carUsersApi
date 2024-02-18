package com.car.users.api.service;

import java.util.List;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.model.Car;

public interface ICarService {

	List<Car> insert(List<CarDTO> cars, Integer userId);

	Car insert(CarDTO carDTO, Integer userId);

	Car insert(Car car);

	List<Car> find(Integer userId);

	Car find(Integer id, Integer userId);

	void delete(Integer userId);

	void delete(Integer id, Integer userId);

	Car update(Integer id, Integer userId, CarDTO carDTO);

}
