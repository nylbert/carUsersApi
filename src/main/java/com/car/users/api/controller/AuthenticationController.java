package com.car.users.api.controller;

import java.time.LocalDate;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.users.api.domain.dto.AuthenticationDTO;
import com.car.users.api.domain.dto.ErrorResponseDTO;
import com.car.users.api.domain.dto.LoginResponseDTO;
import com.car.users.api.domain.model.User;
import com.car.users.api.infra.security.JwtTokenService;
import com.car.users.api.service.IUserService;
import com.car.users.api.service.UserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/signin")
@Tag(name = "API de Login")
public class AuthenticationController {

	private AuthenticationManager authenticationManager;
	private JwtTokenService jwtTokenService;
	private IUserService userService;
	
	public AuthenticationController(AuthenticationManager authenticationManager, JwtTokenService jwtTokenService,
			UserService userService) {
		super();
		this.authenticationManager = authenticationManager;
		this.jwtTokenService = jwtTokenService;
		this.userService = userService;
	}


	@PostMapping
	@Operation(summary = "Enpoint de login do usuário", description = "Informe o login e senha atraves de um json e faça seu login", tags = { "auth" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = LoginResponseDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
	public ResponseEntity<LoginResponseDTO> login(@RequestBody AuthenticationDTO authenticationDTO) {
		var usernamePassword = new UsernamePasswordAuthenticationToken(authenticationDTO.login(), authenticationDTO.password());
		var auth = this.authenticationManager.authenticate(usernamePassword);
		var user = (User) auth.getPrincipal();
		var token = this.jwtTokenService.generateToken(user);
		
		user.setLastLogin(LocalDate.now());
		this.userService.update(user);
		
		return new ResponseEntity<>(new LoginResponseDTO(token, user.getUsername()), HttpStatus.OK);
	}
	
}
