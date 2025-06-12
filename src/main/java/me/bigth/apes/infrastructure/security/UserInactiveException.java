package me.bigth.apes.infrastructure.security;

import org.springframework.security.authentication.AccountStatusException;

public class UserInactiveException extends AccountStatusException {
    public UserInactiveException(String msg) {
        super(msg);
    }

    public UserInactiveException(String msg, Throwable cause) {
        super(msg, cause);
    }
}
