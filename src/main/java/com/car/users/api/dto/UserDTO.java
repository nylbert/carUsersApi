package com.car.users.api.dto;

import java.util.Date;
import java.util.List;

import com.car.users.api.model.Car;

import lombok.Data;

@Data
public class UserDTO {

	private String firstName;
	private String lastName;
	private String email;
	private Date birthday;
	private String login;
	private String password;
	private String phone;
	private List<Car> cars;

}
