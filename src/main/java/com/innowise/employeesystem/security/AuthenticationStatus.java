package com.innowise.employeesystem.security;

import com.innowise.employeesystem.util.MessageConstant;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum AuthenticationStatus {
    OK(MessageConstant.LOGIN_MESSAGE),
    USER_NOT_FOUND(MessageConstant.USER_NOT_FOUND),
    INCORRECT_PASSWORD(MessageConstant.INCORRECT_PASSWORD);

    private final String message;
}
