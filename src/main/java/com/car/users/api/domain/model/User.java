package com.car.users.api.domain.model;

import java.util.Date;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Data;

@Data
@Entity
@Table(name = "app_user")
public class User {

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
	private Integer id;
	
	@Column(name = "first_name", length = 50, nullable = false)
	private String firstName;
	
	@Column(name = "last_name", length = 50, nullable = false)
	private String lastName;
	
	@Column(name = "email", nullable = false)
	private String email;
	
	@Column(name = "birthday", nullable = false)
	private Date birthday;
	
	@Column(name = "login", nullable = false)
	private String login;
	
	@Column(name = "password", nullable = false)
	private String password;
	
	@Column(name = "phone", nullable = false)
	private String phone;
	
	public void setEncryptedPassword(String password) {
		this.password = new BCryptPasswordEncoder().encode(password);
	}

}
