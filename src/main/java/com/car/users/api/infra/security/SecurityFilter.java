package com.car.users.api.infra.security;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.car.users.api.domain.model.User;
import com.car.users.api.service.IUserService;
import com.car.users.api.service.UserService;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class SecurityFilter extends OncePerRequestFilter {
	
	private JwtTokenService jwtTokenService;
	private IUserService userService;
	private HandlerExceptionResolver exceptionResolver;

	public SecurityFilter(JwtTokenService jwtTokenService, UserService userService,
			@Qualifier("handlerExceptionResolver") HandlerExceptionResolver exceptionResolver) {
		this.jwtTokenService = jwtTokenService;
		this.userService = userService;
		this.exceptionResolver = exceptionResolver;
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException {
		var token = this.recoverToken(request);
		
		if(token == null) {
			response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.getWriter().write(HttpStatus.UNAUTHORIZED.name());
            return;
        }
		
		try {
			var login = this.jwtTokenService.validateToken(token);
			User user = this.userService.find(login);
			
			var authentication = new UsernamePasswordAuthenticationToken(user, null, user.getAuthorities());
			SecurityContextHolder.getContext().setAuthentication(authentication);
			filterChain.doFilter(request, response);
		
		} catch (JWTVerificationException e) {
			exceptionResolver.resolveException(request, response, null, e);
		}
			
		
	}
	
	private String recoverToken(HttpServletRequest httpServletRequest) {
		var authHeader = httpServletRequest.getHeader("Authorization");
		if(authHeader == null) return null;
		return authHeader.replace("Bearer ", "");
	}
	
}
