package com.car.users.api.service;

import java.util.List;

import com.car.users.api.dto.UserDTO;

public interface IUserService {

	UserDTO findUserByLogin(String login);

	UserDTO insert(UserDTO userDTO);

	List<UserDTO> findAll();

	UserDTO findById(Long id);

	void deleteById(Long id);

	UserDTO updateById(Long id, UserDTO userDTO);

}
