package com.car.users.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.PRECONDITION_FAILED)
public class DuplicatedFieldException extends RuntimeException {

    private static final long serialVersionUID = 1L;

	public DuplicatedFieldException(String field) {
        super(field + " already exists");
    }
}