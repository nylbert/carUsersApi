package com.car.users.api.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.car.users.api.constant.UserConstants;
import com.car.users.api.dto.CarDTO;
import com.car.users.api.dto.UserDTO;
import com.car.users.api.exception.DuplicatedFieldException;
import com.car.users.api.exception.InvalidFieldException;
import com.car.users.api.exception.InvalidLoginException;
import com.car.users.api.exception.RequiredFieldException;
import com.car.users.api.mapper.CarMapper;
import com.car.users.api.mapper.UserMapper;
import com.car.users.api.model.Car;
import com.car.users.api.model.User;
import com.car.users.api.repository.UserRepository;
import com.car.users.api.util.UserUtils;

import io.micrometer.common.util.StringUtils;

@Service
public class UserService implements IUserService {

	private UserRepository userRepository;

	public UserService(UserRepository userRepository) {
		this.userRepository = userRepository;
	}

	@Override
	public UserDTO findUserByLogin(String login) {
		User user = userRepository.findByLogin(login);
		if (user == null) {
			throw new InvalidLoginException();
		}
		return UserMapper.INSTANCE.userToUserDto(user);
	}

	@Override
	public UserDTO insert(UserDTO userDTO) {
		validateRequiredFields(userDTO);
		validateInvalidFields(userDTO);
		validateUniqueness(userDTO);

		User user = UserMapper.INSTANCE.userDtoToUser(userDTO);
		return UserMapper.INSTANCE.userToUserDto(this.userRepository.save(user));
	}

	@Override
	public List<UserDTO> findAll() {
		Iterable<User> users = this.userRepository.findAll();

		List<UserDTO> userDTOs = new ArrayList<>();
		for (User user : users) {
			UserDTO dto = UserMapper.INSTANCE.userToUserDto(user);
			userDTOs.add(dto);
		}
		return userDTOs;
	}

	@Override
	public UserDTO findById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		return UserMapper.INSTANCE.userToUserDto(user.get());
	}

	@Override
	public void deleteById(Long id) {
		Optional<User> user = this.userRepository.findById(id);
		this.userRepository.delete(user.get());
	}

	@Override
	public UserDTO updateById(Long id, UserDTO userDTO) {
		validateRequiredFields(userDTO);
		validateInvalidFields(userDTO);
		validateUniqueness(userDTO);

		Optional<User> optionalUser = this.userRepository.findById(id);
		User user = optionalUser.get();

		addUpdatedFields(userDTO, user);

		User updatedUser = this.userRepository.save(user);

		return UserMapper.INSTANCE.userToUserDto(updatedUser);
	}

	private void addUpdatedFields(UserDTO userDTO, User user) {
		user.setBirthday(userDTO.getBirthday());
		user.setEmail(userDTO.getEmail());
		user.setFirstName(userDTO.getFirstName());
		user.setLastName(userDTO.getLastName());
		user.setLogin(userDTO.getLogin());
		user.setPassword(userDTO.getPassword());
		user.setPhone(userDTO.getPhone());

		List<Car> cars = new ArrayList<Car>();

		for (CarDTO carDTO : userDTO.getCars()) {
			Car car = CarMapper.INSTANCE.carDtoToCar(carDTO);
			car.setOwner(user);
			cars.add(car);
		}

		user.setCars(cars);
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
		if (StringUtils.isNotBlank(userDTO.getEmail())) {
			boolean emailExists = this.userRepository.countByEmail(userDTO.getEmail()) > 0;

			if (emailExists) {
				throw new DuplicatedFieldException(UserConstants.EMAIL);
			}
		}

		if (StringUtils.isNotBlank(userDTO.getLogin())) {
			boolean loginExists = this.userRepository.countByLogin(userDTO.getLogin()) > 0;

			if (loginExists) {
				throw new DuplicatedFieldException(UserConstants.LOGIN);
			}
		}
	}
}
