package com.car.users.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.User;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.users.api.domain.dto.AuthenticationDTO;
import com.car.users.api.domain.dto.LoginResponseDTO;
import com.car.users.api.infra.security.JwtTokenService;

@RestController
@RequestMapping("/signin")
public class AuthenticationController {

	private AuthenticationManager authenticationManager;
	private JwtTokenService jwtTokenService;
	
	public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService) {
		this.authenticationManager = authenticationManager;
		this.jwtTokenService = jwtTokenService;
	}
	
	@PostMapping
	public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var user = (User) auth.getPrincipal();
		var token = this.jwtTokenService.generateToken(user);
		
		return new ResponseEntity<>(new LoginResponseDTO(token, user.getUsername()), HttpStatus.OK);
	}
	
}
