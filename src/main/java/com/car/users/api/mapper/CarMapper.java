package com.car.users.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.car.users.api.dto.CarDTO;
import com.car.users.api.model.Car;

@Mapper
public interface CarMapper {
	
	CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

	Car carDtoToCar(CarDTO carDTO) ;
	
	CarDTO carToCarDto(Car car);
}
