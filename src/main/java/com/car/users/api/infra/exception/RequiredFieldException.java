package com.car.users.api.infra.exception;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class RequiredFieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public RequiredFieldException(List<String> fields) {
        super("Missing fields: " + String.join(" - ", fields));
    }
}