package com.car.users.api.service;

import org.springframework.stereotype.Service;

import com.car.users.api.repository.UserRepository;

@Service
public class UserService implements IUserService {
	
	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}
}
