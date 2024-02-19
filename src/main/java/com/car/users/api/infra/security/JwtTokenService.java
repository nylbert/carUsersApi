package com.car.users.api.infra.security;

import java.util.Calendar;
import java.util.Date;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

@Service
public class JwtTokenService {
	
	@Value("${jwt.secret}")
	private String secret;
	
	@Value("${jwt.expiration}")
	private Integer expiration;

	public String generateToken(UserDetails userDetails) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		Date date = getExpirationDate();
		
		String token = JWT.create()
				.withIssuer("auth-api")
				.withSubject(userDetails.getUsername())
				.withExpiresAt(date)
				.sign(algorithm);
		
		return token;
	}
	
	public String validateToken(String token) {
		Algorithm algorithm = Algorithm.HMAC256(secret);
		
		return JWT.require(algorithm)
				.withIssuer("auth-api")
				.build()
				.verify(token)
				.getSubject();
	}

	private Date getExpirationDate() {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date());
		calendar.add(Calendar.MILLISECOND, expiration);
		return calendar.getTime();
	}
	
}
