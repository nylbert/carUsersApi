package com.car.users.api.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.users.api.domain.dto.ErrorResponseDTO;
import com.car.users.api.domain.dto.LoggedUserDTO;
import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.domain.model.User;
import com.car.users.api.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/me")
@Tag(name = "API do Usuário Logado")
public class AccountController {

	private IUserService userService;
	
	public AccountController(IUserService userService) {
		this.userService = userService;
	}

	@GetMapping
	@Operation(summary = "Enpoint de informações do usuário logado", description = "Retorna informações do usuário logado", tags = { "account" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = LoggedUserDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "401", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
	public ResponseEntity<LoggedUserDTO> detailLoggedUser() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		var user = (User) authentication.getPrincipal();

		UserDTO userDTO = this.userService.find(user.getId());

		LoggedUserDTO loggedUser = new LoggedUserDTO(user.getFirstName(), user.getLastName(), user.getEmail(),
				user.getBirthday(), user.getLogin(), user.getPassword(), user.getPhone(), userDTO.getCars(),
				user.getCreatedAt(), user.getLastLogin());

		return new ResponseEntity<>(loggedUser, HttpStatus.OK);
	}
	
}
