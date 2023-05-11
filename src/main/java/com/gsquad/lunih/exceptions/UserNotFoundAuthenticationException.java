package com.gsquad.lunih.exceptions;

import org.springframework.security.core.AuthenticationException;

public class UserNotFoundAuthenticationException extends AuthenticationException {

    public UserNotFoundAuthenticationException(String msg, Throwable t) {
        super(msg, t);
    }

    public UserNotFoundAuthenticationException(String msg) {
        super(msg);
    }
}
