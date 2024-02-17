package com.car.users.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.car.users.api.dto.CarDTO;
import com.car.users.api.model.Car;

@Mapper
public interface CarMapper {
	
	CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "owner", ignore = true)
	Car carDtoToCar(CarDTO carDTO) ;
	
	CarDTO carToCarDto(Car car);
}
