package com.car.users.api.controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.model.User;
import com.car.users.api.service.ICarService;

@RestController
@RequestMapping("/cars")
public class CarController {

	private ICarService carService;

	public CarController(ICarService carService) {
		this.carService = carService;
	}

	@GetMapping
	public ResponseEntity<List<CarDTO>> listCars() {
		var cars = this.carService.find(getUserId());
		return new ResponseEntity<List<CarDTO>>(cars, HttpStatus.OK);
	}

	@PostMapping
	public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
		var car = this.carService.insert(carDTO, getUserId());
		return new ResponseEntity<>(car, HttpStatus.OK);
	}

	@GetMapping("{id}")
	public ResponseEntity<CarDTO> listCarsById(@PathVariable Integer id) {
		var car = this.carService.find(id, getUserId());
		return new ResponseEntity<CarDTO>(car, HttpStatus.OK);
	}

	@DeleteMapping("{id}")
	public ResponseEntity<String> deleteCarsById(@PathVariable Integer id) {
		this.carService.delete(id, getUserId());
		return new ResponseEntity<String>(HttpStatus.OK);
	}

	@PutMapping("{id}")
	public ResponseEntity<CarDTO> updateCarById(@PathVariable Integer id, @RequestBody CarDTO carDTO) {
		CarDTO car = this.carService.update(id, getUserId(), carDTO);
		return new ResponseEntity<CarDTO>(car, HttpStatus.OK);
	}
	
	private Integer getUserId() {
		var authentication = SecurityContextHolder.getContext().getAuthentication();
		var user = (User) authentication.getPrincipal();
		return user.getId();
	}

}
