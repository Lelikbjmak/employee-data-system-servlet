package com.innowise.employeesystem.security;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum SessionStatus {
    OK("Session is valid"),
    NOT_FOUND("Session is not found."),
    EXPIRED("Session expired.");

    private final String message;
}
