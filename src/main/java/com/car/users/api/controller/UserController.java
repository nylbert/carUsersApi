package com.car.users.api.controller;

import java.io.IOException;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.dto.ErrorResponseDTO;
import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.service.IUserService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/users")
@Tag(name = "API CRUD de usuários")
public class UserController {

	private IUserService userService;
	
	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	@Operation(summary = "Listar usuários", description = "Listagem de todos os usuários", tags = { "users" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
	public ResponseEntity<List<UserDTO>> listUsers(){
		List<UserDTO> users = this.userService.find();
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}
	
	@PostMapping
	@Operation(summary = "Criar usuário", description = "Cria um usuário baseado no json enviado no body da requisição", tags = { "users" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		UserDTO user = this.userService.insert(userDTO);
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	@Operation(summary = "Buscar usuário por ID", description = "Encontre um usuário especifico passando o id como um parametro da URL", tags = { "users" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
	public ResponseEntity<UserDTO> listById(@PathVariable Integer id){
		UserDTO user = this.userService.find(id);
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	@Operation(summary = "Deletar Usuário", description = "Remove um usuário selecionado por id passado na URL", tags = { "users" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
	public ResponseEntity<?> deleteById(@PathVariable Integer id){
		this.userService.delete(id);
		return ResponseEntity.ok().build();
	}
	
	@PutMapping("{id}")
	@Operation(summary = "Atualizar Usuário", description = "Atualiza os dados de um usuário baseado no json enviado no body da requisição", tags = { "users" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = UserDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
	public ResponseEntity<UserDTO> updateUser(@PathVariable Integer id, @RequestBody UserDTO userDTO) {
		UserDTO user = this.userService.update(id, userDTO);
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@PutMapping("{id}/image")
	@Operation(summary = "Upload da Foto do Usuário", description = "Atualiza a foto de um usuário baseado na imagem enviado no body da requisição via multipart file", tags = { "cars" })
	@ApiResponses({
	      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
	      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
	public ResponseEntity<?> uploadUserImage(@PathVariable Integer id, @RequestParam("image") MultipartFile imageFile) {
		try {
			userService.updateUserImage(id, imageFile);
			return ResponseEntity.ok().build();
		} catch (IOException e) {
			return ResponseEntity.badRequest().body("Could not upload image");
		}
	}
}
