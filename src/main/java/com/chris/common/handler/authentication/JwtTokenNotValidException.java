package com.chris.common.handler.authentication;

import javax.naming.AuthenticationException;

//khi nào xong test thử AuthenticationException của spring security core (Ctrl + Space để tìm)
public class JwtTokenNotValidException extends AuthenticationException {

    public JwtTokenNotValidException(String msg) {
        super(msg);
    }
}
