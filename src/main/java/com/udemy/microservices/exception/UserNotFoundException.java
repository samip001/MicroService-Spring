package com.udemy.microservices.exception;

import lombok.Data;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
@Data
public class UserNotFoundException extends Exception {

    public static final long serialVersionUID = 1L;

    private String message;

    public UserNotFoundException(String message) {
        super();
        this.message = message;

    }
}
