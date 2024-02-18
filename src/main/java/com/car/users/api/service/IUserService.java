package com.car.users.api.service;

import java.util.List;

import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.domain.model.User;

public interface IUserService {

	User find(String login);

	List<UserDTO> find();

	UserDTO find(Integer id);

	UserDTO insert(UserDTO userDTO);

	UserDTO update(Integer id, UserDTO userDTO);

	void delete(Integer id);

}
