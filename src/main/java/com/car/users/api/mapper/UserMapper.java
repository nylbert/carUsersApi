package com.car.users.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import com.car.users.api.dto.UserDTO;
import com.car.users.api.model.User;

@Mapper
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	User userDtoToUser(UserDTO userDTO) ;
	
	UserDTO userToUserDto(User car);
	
}
