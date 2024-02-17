package com.car.users.api.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

import com.car.users.api.dto.UserDTO;
import com.car.users.api.model.User;

@Mapper
public interface UserMapper {
	
	UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

	@Mapping(target = "id", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "encryptedPassword", source = "password")
	User userDtoToUser(UserDTO userDTO, @MappingTarget User user);
	
	@Mapping(target = "id", ignore = true)
	@Mapping(target = "password", ignore = true)
	@Mapping(target = "encryptedPassword", source = "password")
	User userDtoToUser(UserDTO userDTO);
	
	@Mapping(target = "cars", ignore = true)
	UserDTO userToUserDto(User user);
	
}
