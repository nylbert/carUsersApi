package com.car.users.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.car.users.api.constant.UserConstants;
import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.domain.mapper.CarMapper;
import com.car.users.api.domain.mapper.UserMapper;
import com.car.users.api.domain.model.Car;
import com.car.users.api.domain.model.User;
import com.car.users.api.infra.exception.DuplicatedFieldException;
import com.car.users.api.infra.exception.InvalidFieldException;
import com.car.users.api.infra.exception.RequiredFieldException;
import com.car.users.api.repository.CarRepository;
import com.car.users.api.repository.UserRepository;
import com.car.users.api.util.UserUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class UserService implements IUserService {

	private UserRepository userRepository;
	private CarRepository carRepository;

	public UserService(UserRepository userRepository, CarRepository carRepository) {
		this.userRepository = userRepository;
		this.carRepository = carRepository;
	}

	@Override
	public User find(String login) {
		User user = userRepository.findByLogin(login);
		if (user == null) {
			throw new RuntimeException();
		}
		return user;
	}

	@Override
	public List<UserDTO> find() {
		Iterable<User> users = this.userRepository.findAll();

		List<UserDTO> userDTOs = new ArrayList<>();
		
		for (User user : users) {
			UserDTO userDTO = UserMapper.INSTANCE.userToUserDto(user);
			List<CarDTO> cars = CarMapper.INSTANCE.carToCarDto(this.carRepository.findByUserId(user.getId()));
			userDTO.setCars(cars);
			userDTOs.add(userDTO);
		}
		
		return userDTOs;
	}

	@Override
	public UserDTO find(Integer id) {
		Optional<User> user = this.userRepository.findById(id);
		UserDTO userDTO = UserMapper.INSTANCE.userToUserDto(user.get());
		List<CarDTO> cars = CarMapper.INSTANCE.carToCarDto(this.carRepository.findByUserId(id));
		userDTO.setCars(cars);
		return userDTO;
	}


	@Override
	public UserDTO insert(UserDTO userDTO) {
		validateRequiredFields(userDTO);
		validateInvalidFields(userDTO);
		validateUniqueness(userDTO);

		User user = UserMapper.INSTANCE.userDtoToUser(userDTO);
		User newUser = this.userRepository.save(user);
		
		List<Car> cars = insertCars(userDTO, newUser);
		
		UserDTO newUserDTO = UserMapper.INSTANCE.userToUserDto(newUser);
		newUserDTO.setCars(CarMapper.INSTANCE.carToCarDto(cars));

		return newUserDTO;
	}

	@Override
	public void delete(Integer id) {
		Optional<User> user = this.userRepository.findById(id);
		this.userRepository.delete(user.get());
	}

	@Override
	public UserDTO update(Integer id, UserDTO userDTO) {
		validateRequiredFields(userDTO);
		validateInvalidFields(userDTO);
		validateUniqueness(id, userDTO);

		Optional<User> optionalUser = this.userRepository.findById(id);
		User user = optionalUser.get();

		UserMapper.INSTANCE.userDtoToUser(userDTO, user);
		User updatedUser = this.userRepository.save(user);

		List<Car> deletedCars = this.carRepository.findByUserId(id);
		deletedCars.forEach(car -> this.carRepository.delete(car));
		
		List<Car> cars = insertCars(userDTO, user);
		
		UserDTO updatedUserDTO = UserMapper.INSTANCE.userToUserDto(updatedUser);
		updatedUserDTO.setCars(CarMapper.INSTANCE.carToCarDto(cars));
		
		return updatedUserDTO;
	}

	private List<Car> insertCars(UserDTO userDTO, User user) {
		List<Car> cars = List.of();
		
		userDTO.getCars().forEach(carDTO -> {
			Car car = CarMapper.INSTANCE.carDtoToCar(carDTO, user.getId());
			cars.add(this.carRepository.save(car));
		});
		return cars;
	}

	private void validateInvalidFields(UserDTO userDTO) {
		List<String> fields = new ArrayList<>();

		if (!UserUtils.isValidEmail(userDTO.getEmail()) || !UserUtils.isValidEmail(userDTO.getEmail())) {
			fields.add(UserConstants.EMAIL);
		}

		if (!UserUtils.isValidPhone(userDTO.getPhone())) {
			fields.add(UserConstants.PHONE);
		}

		if (!UserUtils.isValidLength(userDTO.getFirstName(), 50)) {
			fields.add(UserConstants.FIRST_NAME);
		}

		if (!UserUtils.isValidLength(userDTO.getLastName(), 50)) {
			fields.add(UserConstants.LAST_NAME);
		}

		if (!UserUtils.isValidPassword(userDTO.getPassword())) {
			fields.add(UserConstants.PASSWORD);
		}

		if (fields.size() > 0) {
			throw new InvalidFieldException(fields);
		}
	}

	private void validateRequiredFields(UserDTO userDTO) {
		List<String> fields = new ArrayList<>();

		if (StringUtils.isBlank(userDTO.getFirstName())) {
			fields.add(UserConstants.FIRST_NAME);
		}
		if (StringUtils.isBlank(userDTO.getLastName())) {
			fields.add(UserConstants.LAST_NAME);
		}
		if (StringUtils.isBlank(userDTO.getEmail())) {
			fields.add(UserConstants.EMAIL);
		}
		if (StringUtils.isBlank(userDTO.getLogin())) {
			fields.add(UserConstants.LOGIN);
		}
		if (StringUtils.isBlank(userDTO.getPassword())) {
			fields.add(UserConstants.PASSWORD);
		}
		if (StringUtils.isBlank(userDTO.getPhone())) {
			fields.add(UserConstants.PHONE);
		}
		if (userDTO.getBirthday() == null) {
			fields.add(UserConstants.BIRTHDAY);
		}

		if (fields.size() > 0) {
			throw new RequiredFieldException(fields);
		}
	}

	private void validateUniqueness(UserDTO userDTO) {
		validateUniqueness(null, userDTO);
	}

	private void validateUniqueness(Integer id, UserDTO userDTO) {
		Long countEmail = id == null ? this.userRepository.countByEmail(userDTO.getEmail())
				: this.userRepository.countByEmailAndIdNot(userDTO.getEmail(), id);
		boolean emailExists = countEmail > 0;

		if (emailExists) {
			throw new DuplicatedFieldException(UserConstants.EMAIL);
		}

		Long countLogin = id == null ? this.userRepository.countByLogin(userDTO.getLogin())
				: this.userRepository.countByLoginAndIdNot(userDTO.getLogin(), id);
		boolean loginExists = countLogin > 0;

		if (loginExists) {
			throw new DuplicatedFieldException(UserConstants.LOGIN);
		}
	}
}
