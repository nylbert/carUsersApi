package com.car.users.api.domain.mapper;

import java.util.List;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.model.Car;

@Mapper
public interface CarMapper {
	
	CarMapper INSTANCE = Mappers.getMapper(CarMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "userId", source = "userId")
	Car carDtoToCar(CarDTO carDTO, Integer userId);
	
	CarDTO carToCarDto(Car car);
	
	List<CarDTO> carToCarDto(List<Car> cars);
}
