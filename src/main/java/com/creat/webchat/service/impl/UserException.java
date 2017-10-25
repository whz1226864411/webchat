package com.creat.webchat.service.impl;

/**
 * Created by whz on 2017/9/24.
 */
public class UserException extends Exception{

    public static final String USER_NAME_HAS_BEEN_REGISTER = "100";
    public UserException(String message) {
        super(message);
    }
}
