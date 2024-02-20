package com.car.users.api.service;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.domain.model.User;

public interface IUserService {

	User find(String login);

	List<UserDTO> find();

	UserDTO find(Integer id);

	UserDTO insert(UserDTO userDTO);

	UserDTO update(Integer id, UserDTO userDTO);

	void delete(Integer id);

	void update(User user);

	void deleteUsers();

	void updateUserImage(Integer id, MultipartFile imageFile) throws IOException;

}
