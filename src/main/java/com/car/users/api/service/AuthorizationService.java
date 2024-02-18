package com.car.users.api.service;

import java.util.List;

import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import com.car.users.api.domain.dto.UserDTO;

@Service
public class AuthorizationService implements UserDetailsService {

	private IUserService userService;

	public AuthorizationService(UserService userService) {
		this.userService = userService;
	}

	@Override
	public UserDetails loadUserByUsername(String username) {
		UserDTO userDTO = this.userService.findUserByLogin(username);
		return new User(username, userDTO.getPassword(), List.of());
	}

}
