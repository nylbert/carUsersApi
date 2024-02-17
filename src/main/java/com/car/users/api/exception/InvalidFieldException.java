package com.car.users.api.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidFieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public InvalidFieldException(List<String> fields) {
        super("Invalid fields: " + String.join(" - ", fields));
    }
}