package com.innowise.employeesystem.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.dto.RegistrationDto;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.provider.ObjectMapperProvider;
import com.innowise.employeesystem.service.EmployeeRegistrationService;
import com.innowise.employeesystem.serviceimpl.EmployeeRegistrationServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

public class RegisterNewEmployeeCommand extends EmployeeCommand {

    private static RegisterNewEmployeeCommand instance;

    private final ResponseServiceImpl responseService;

    private final EmployeeRegistrationService employeeRegistrationService;

    private final ObjectMapper objectMapper;

    private RegisterNewEmployeeCommand() {
        super(Set.of(RoleEnum.ROLE_ADMIN));
        this.responseService = ResponseServiceImpl.getInstance();
        this.employeeRegistrationService = EmployeeRegistrationServiceImpl.getInstance();
        this.objectMapper = ObjectMapperProvider.getInstance();
    }

    public static RegisterNewEmployeeCommand getInstance() {
        if (instance == null)
            instance = new RegisterNewEmployeeCommand();

        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            RegistrationDto registrationDto = objectMapper.readValue(request.getInputStream(), RegistrationDto.class);
            EmployeeDto registeredEmployee = employeeRegistrationService.registerNewEmployee(registrationDto);
            responseService.processResponse(response, registeredEmployee, 200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
