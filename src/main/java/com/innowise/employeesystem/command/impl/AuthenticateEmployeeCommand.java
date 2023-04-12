package com.innowise.employeesystem.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.dto.AuthenticationRequest;
import com.innowise.employeesystem.dto.Response;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.provider.ObjectMapperProvider;
import com.innowise.employeesystem.service.AuthenticationService;
import com.innowise.employeesystem.serviceimpl.AuthenticationServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

public class AuthenticateEmployeeCommand extends EmployeeCommand {

    private static AuthenticateEmployeeCommand instance;

    private final ResponseServiceImpl responseService;

    private final ObjectMapper objectMapper;

    private final AuthenticationService authenticationService;

    public static AuthenticateEmployeeCommand getInstance() {
        if (instance == null)
            instance = new AuthenticateEmployeeCommand();

        return instance;
    }

    public AuthenticateEmployeeCommand() {
        super(Set.of(RoleEnum.ROLE_GUEST));
        this.responseService = ResponseServiceImpl.getInstance();
        this.objectMapper = ObjectMapperProvider.getInstance();
        this.authenticationService = AuthenticationServiceImpl.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            AuthenticationRequest authenticationRequest = objectMapper.readValue(request.getInputStream(), AuthenticationRequest.class);
            Response authenticationResponse = authenticationService.authenticate(authenticationRequest, request);
            responseService.processResponse(response, authenticationResponse, authenticationResponse.code());
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
