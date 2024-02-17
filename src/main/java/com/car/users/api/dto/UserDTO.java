package com.car.users.api.dto;

import java.util.Date;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.Data;

@Data
public class UserDTO {

	private String firstName;
	private String lastName;
	private String email;
	
	@JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy", timezone = "UTC")
    private Date birthday;
	
	private String login;
	private String password;
	private String phone;
	private List<CarDTO> cars;

}
