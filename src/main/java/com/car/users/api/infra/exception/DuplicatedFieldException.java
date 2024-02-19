package com.car.users.api.infra.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.CONFLICT)
public class DuplicatedFieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public DuplicatedFieldException(String field) {
        super(field + " already exists");
    }
}