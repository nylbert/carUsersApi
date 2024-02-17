package com.car.users.api.infra.security;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTCreationException;
import com.auth0.jwt.exceptions.JWTVerificationException;

@Service
public class JwtTokenService {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Integer expiration;

	public String generateToken(UserDetails userDetails) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			Date date = getExpirationDate();
			
			String token = JWT.create()
					.withIssuer("auth-api")
					.withSubject(userDetails.getUsername())
					.withExpiresAt(date)
					.sign(algorithm);
			
			return token;
		} catch (JWTCreationException e) {
			throw new RuntimeException("Error during genereation token", e);
		}
	}
	
	public String validateToken(String token) {
		try {
			Algorithm algorithm = Algorithm.HMAC256(secret);
			
			return JWT.require(algorithm)
					.withIssuer("auth-api")
					.build()
					.verify(token)
					.getSubject();
		} catch (JWTVerificationException e) {
			return "";
		}
	}

	private Date getExpirationDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MILLISECOND, expiration);
		return calendar.getTime();
	}
	
}
