package com.car.users.api.infra.exception;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.car.users.api.domain.dto.ErrorResponseDTO;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(RequiredFieldException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleRequiredFieldException(RequiredFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponseDTO(ex.getMessage(), 400));
    }

    @ExceptionHandler(AuthenticationException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleIAuthenticationException(AuthenticationException ex) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                             .body(new ErrorResponseDTO("Invalid login or password", 401));
    }
    
    @ExceptionHandler(InvalidFieldException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleInvalidFieldException(InvalidFieldException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                             .body(new ErrorResponseDTO(ex.getMessage(), 400));
    }

    @ExceptionHandler(DuplicatedFieldException.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleDuplicatedFieldException(DuplicatedFieldException ex) {
        return ResponseEntity.status(HttpStatus.CONFLICT)
                             .body(new ErrorResponseDTO(ex.getMessage(), 409));
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public ResponseEntity<ErrorResponseDTO> handleGenericException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                             .body(new ErrorResponseDTO("Internal Server Error", 500));
    }

}

