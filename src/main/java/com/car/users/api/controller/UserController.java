package com.car.users.api.controller;

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
import org.springframework.web.bind.annotation.RestController;

import com.car.users.api.dto.UserDTO;
import com.car.users.api.service.IUserService;

@RestController
@RequestMapping("/users")
public class UserController {

	private IUserService userService;
	
	public UserController(IUserService userService) {
		this.userService = userService;
	}
	
	@GetMapping
	public ResponseEntity<List<UserDTO>> listUsers(){
		List<UserDTO> users = this.userService.findAll();
		return new ResponseEntity<List<UserDTO>>(users, HttpStatus.OK);
	}
	
	@PostMapping
	public ResponseEntity<UserDTO> createUser(@RequestBody UserDTO userDTO) {
		UserDTO user = this.userService.insert(userDTO);
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@GetMapping("{id}")
	public ResponseEntity<UserDTO> listById(@PathVariable Long id){
		UserDTO user = this.userService.findById(id);
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	@DeleteMapping("{id}")
	public ResponseEntity<UserDTO> deleteById(@PathVariable Long id){
		this.userService.deleteById(id);
		return new ResponseEntity<UserDTO>(HttpStatus.OK);
	}
	
	@PutMapping("{id}")
	public ResponseEntity<UserDTO> updateUser(@PathVariable Long id, @RequestBody UserDTO userDTO) {
		UserDTO user = this.userService.updateById(id, userDTO);
		return new ResponseEntity<UserDTO>(user, HttpStatus.OK);
	}
	
	
}
