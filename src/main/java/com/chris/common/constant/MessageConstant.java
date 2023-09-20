package com.chris.common.constant;

import org.springframework.stereotype.Component;

@Component
public interface MessageConstant  {
    String INVALID_TOKEN = "Invalid Token";
    String VALID_TOKEN = "Valid token for user ";
    String USERNAME_OR_PASSWORD_EMPTY = "Username or Password should not be empty";
    String USERNAME_OR_PASSWORD_INVALID = "Bad credentials";
    String USERNAME_INACTIVE = "User is inactive";
    String USERNAME_NOT_FOUND = "User not found";
    String USERNAME_EXISTED = "Username existed";
    String SYSTEM_ERROR = "System error";
    String SUCCESS = "Success";

}
