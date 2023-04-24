package com.gsquad.lunih.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import java.util.List;

@ResponseStatus(HttpStatus.BAD_REQUEST)
public class InvalidException extends RuntimeException {
    public InvalidException() {
    }

    public InvalidException(String message) {
        super(message);
    }

    public InvalidException(List<String> error) {
        super(String.valueOf(error));
    }
}
