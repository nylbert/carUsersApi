package com.car.users.api.service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.car.users.api.constant.UserConstants;
import com.car.users.api.domain.dto.CarDTO;
import com.car.users.api.domain.dto.UserDTO;
import com.car.users.api.domain.mapper.UserMapper;
import com.car.users.api.domain.model.User;
import com.car.users.api.infra.exception.DuplicatedFieldException;
import com.car.users.api.infra.exception.InvalidFieldException;
import com.car.users.api.infra.exception.RequiredFieldException;
import com.car.users.api.repository.UserRepository;
import com.car.users.api.util.UserUtils;

import io.micrometer.common.util.StringUtils;
import jakarta.transaction.Transactional;

@Service
public class UserService implements IUserService {

	private UserRepository userRepository;
	private ICarService carService;

	public UserService(UserRepository userRepository, ICarService carService) {
		this.userRepository = userRepository;
		this.carService = carService;
	}

	@Override
	public User find(String login) {
		User user = userRepository.findByLogin(login);
		if (user == null) {
			throw new UsernameNotFoundException("User not found with login: " + login);
		}
		return user;
	}

	@Override
	public List<UserDTO> find() {
		Iterable<User> users = this.userRepository.findAll();

		List<UserDTO> userDTOs = new ArrayList<>();
		
		for (User user : users) {
			UserDTO userDTO = UserMapper.INSTANCE.userToUserDto(user);
			userDTO.setCars(this.carService.find(user.getId()));
			userDTOs.add(userDTO);
		}
		
		return userDTOs;
	}

	@Override
	public UserDTO find(Integer id) {
		UserDTO userDTO = UserMapper.INSTANCE.userToUserDto(findById(id));
		userDTO.setCars(this.carService.find(id));
		return userDTO;
	}

	private User findById(Integer id) {
		return this.userRepository.findById(id)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with id: " + id));
	}

	@Override
	@Transactional
	public UserDTO insert(UserDTO userDTO) {
		validateRequiredFields(userDTO);
		validateInvalidFields(userDTO);
		validateUniqueness(userDTO);

		User user = UserMapper.INSTANCE.userDtoToUser(userDTO);
		user.setCreatedAt(LocalDate.now());
		User newUser = this.userRepository.save(user);
		
		UserDTO newUserDTO = UserMapper.INSTANCE.userToUserDto(newUser);
		newUserDTO.setCars(insertCars(userDTO, newUser));

		return newUserDTO;
	}

	@Override
	@Transactional
	public void delete(Integer id) {
		this.userRepository.delete(findById(id));
	}

	@Override
	@Transactional
	public UserDTO update(Integer id, UserDTO userDTO) {
		validateRequiredFields(userDTO);
		validateInvalidFields(userDTO);
		validateUniqueness(id, userDTO);
		
		User user = findById(id);

		UserMapper.INSTANCE.userDtoToUser(userDTO, user);
		User updatedUser = this.userRepository.save(user);

		this.carService.delete(id);
		
		UserDTO updatedUserDTO = UserMapper.INSTANCE.userToUserDto(updatedUser);
		updatedUserDTO.setCars(insertCars(userDTO, user));
		
		return updatedUserDTO;
	}
	
	@Override
	public void update(User user) {
		this.userRepository.save(user);
	}

	private List<CarDTO> insertCars(UserDTO userDTO, User user) {
		List<CarDTO> cars = new ArrayList<>();
		
		if(!CollectionUtils.isEmpty(userDTO.getCars())) {
			userDTO.getCars().forEach(carDTO -> {
				cars.add(this.carService.insert(carDTO, user.getId()));
			});
		}
		return cars;
	}

	private void validateInvalidFields(UserDTO userDTO) {
		List<String> fields = new ArrayList<>();

		if (!UserUtils.isValidEmail(userDTO.getEmail())) {
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
	
	@Override
	@Transactional
	public void deleteUsers() {
		this.userRepository.deleteAll();
	}
}
