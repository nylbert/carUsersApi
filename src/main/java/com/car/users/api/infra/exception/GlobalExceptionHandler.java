package com.car.users.api.infra.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.car.users.api.domain.dto.ErrorResponseDTO;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
	
	@ExceptionHandler(JWTVerificationException.class)
	public ResponseEntity<Object> handleJWTVerificationException(JWTVerificationException ex, WebRequest request) {
		return ResponseEntity.status(HttpStatus.FORBIDDEN).body(new ErrorResponseDTO("Unauthorized - invalid session", 401));
	}

    @ExceptionHandler(RequiredFieldException.class)
    public ResponseEntity<ErrorResponseDTO> handleRequiredFieldException(RequiredFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponseDTO(ex.getMessage(), 400));
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<ErrorResponseDTO> handleIAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new ErrorResponseDTO("Invalid login or password", 401));
    }
    
    @ExceptionHandler(InvalidFieldException.class)
    public ResponseEntity<ErrorResponseDTO> handleInvalidFieldException(InvalidFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponseDTO(ex.getMessage(), 400));
    }

    @ExceptionHandler(DuplicatedFieldException.class)
    public ResponseEntity<ErrorResponseDTO> handleDuplicatedFieldException(DuplicatedFieldException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(new ErrorResponseDTO(ex.getMessage(), 409));
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new ErrorResponseDTO("Internal Server Error", 500));
    }

}

