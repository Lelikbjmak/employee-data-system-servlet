package com.innowise.employeesystem.exception;

import lombok.Getter;

import java.time.Instant;
import java.util.Map;
import java.util.NoSuchElementException;

@Getter
public class RoleNotFoundException extends NoSuchElementException {

    private final Instant timestamp;

    private final Map<String, Object> additional;

    public RoleNotFoundException(String message, Instant instant, Map<String, Object> additional) {
        super(message);
        this.timestamp = instant;
        this.additional = additional;
    }
}
