package com.car.users.api.service;

import java.util.List;

import com.car.users.api.domain.dto.CarDTO;

public interface ICarService {

	CarDTO insert(CarDTO carDTO, Integer userId);

	List<CarDTO> find(Integer userId);

	CarDTO find(Integer id, Integer userId);

	void delete(Integer id, Integer userId);

	CarDTO update(Integer id, Integer userId, CarDTO carDTO);

	void delete(Integer userId);

}
