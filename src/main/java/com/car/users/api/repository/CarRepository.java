package com.car.users.api.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.car.users.api.domain.model.Car;

@Repository
public interface CarRepository extends CrudRepository<Car, Integer> {
	
	List<Car> findByUserId(Integer userId);

}
