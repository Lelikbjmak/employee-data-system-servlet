package com.innowise.employeesystem.dto;

public record AuthenticationRequest(
        String username,
        String password) {
}
