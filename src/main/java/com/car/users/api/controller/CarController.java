	package com.car.users.api.controller;
	
	import java.io.IOException;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.car.users.api.domain.dto.CarDTO;
	import com.car.users.api.domain.dto.ErrorResponseDTO;
	import com.car.users.api.domain.model.User;
	import com.car.users.api.service.ICarService;
	
	import io.swagger.v3.oas.annotations.Operation;
	import io.swagger.v3.oas.annotations.media.Content;
	import io.swagger.v3.oas.annotations.media.Schema;
	import io.swagger.v3.oas.annotations.responses.ApiResponse;
	import io.swagger.v3.oas.annotations.responses.ApiResponses;
	import io.swagger.v3.oas.annotations.tags.Tag;
	
	@RestController
	@RequestMapping("/cars")
	@Tag(name = "API CRUD de carros")
	public class CarController {
	
		private ICarService carService;
	
		public CarController(ICarService carService) {
			this.carService = carService;
		}
	
		@GetMapping
		@Operation(summary = "Listar carros", description = "Listagem de todos os carros", tags = { "cars" })
		@ApiResponses({
		      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
		public ResponseEntity<List<CarDTO>> listCars() {
			var cars = this.carService.find(getUserId());
			return new ResponseEntity<List<CarDTO>>(cars, HttpStatus.OK);
		}
	
		@PostMapping
		@Operation(summary = "Criar carro", description = "Cria um carro baseado no json enviado no body da requisição", tags = { "cars" })
		@ApiResponses({
		      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
		public ResponseEntity<CarDTO> createCar(@RequestBody CarDTO carDTO) {
			var car = this.carService.insert(carDTO, getUserId());
			return new ResponseEntity<>(car, HttpStatus.OK);
		}
	
		@GetMapping("{id}")
		@Operation(summary = "Buscar carro por ID", description = "Encontre um carro especifico passando o id como um parametro da URL", tags = { "cars" })
		@ApiResponses({
		      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
		public ResponseEntity<CarDTO> listCarsById(@PathVariable Integer id) {
			var car = this.carService.find(id, getUserId());
			return new ResponseEntity<CarDTO>(car, HttpStatus.OK);
		}
	
		@DeleteMapping("{id}")
		@Operation(summary = "Deletar Carro", description = "Remove um carro selecionado por id passado na URL", tags = { "cars" })
		@ApiResponses({
		      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
		public ResponseEntity<String> deleteCarsById(@PathVariable Integer id) {
			this.carService.delete(id, getUserId());
			return new ResponseEntity<String>(HttpStatus.OK);
		}
	
		@PutMapping("{id}")
		@Operation(summary = "Atualizar Carro", description = "Atualiza os dados de um carro baseado no json enviado no body da requisição", tags = { "cars" })
		@ApiResponses({
		      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "409", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
		public ResponseEntity<CarDTO> updateCarById(@PathVariable Integer id, @RequestBody CarDTO carDTO) {
			CarDTO car = this.carService.update(id, getUserId(), carDTO);
			return new ResponseEntity<CarDTO>(car, HttpStatus.OK);
		}
		
		@PutMapping("{id}/image")
		@Operation(summary = "Upload da Foto do Carro", description = "Atualiza a foto de um carro baseado na imagem enviado no body da requisição via multipart file", tags = { "cars" })
		@ApiResponses({
		      @ApiResponse(responseCode = "200", content = { @Content(schema = @Schema(implementation = CarDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "400", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") }),
		      @ApiResponse(responseCode = "500", content = { @Content(schema = @Schema(implementation = ErrorResponseDTO.class), mediaType = "application/json") })})
		public ResponseEntity<?> uploadCarImage(@PathVariable Integer id, @RequestParam("image") MultipartFile imageFile) {
			try {
				this.carService.updateCarImage(id, imageFile);
				return ResponseEntity.ok().build();
			} catch (IOException e) {
				return ResponseEntity.badRequest().body("Could not upload image");
			}
		}
		
		private Integer getUserId() {
			var authentication = SecurityContextHolder.getContext().getAuthentication();
			var user = (User) authentication.getPrincipal();
			return user.getId();
		}
	
	}
