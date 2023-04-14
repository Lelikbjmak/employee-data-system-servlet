package com.innowise.employeesystem.command.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.provider.ObjectMapperProvider;
import com.innowise.employeesystem.service.EmployeeEditService;
import com.innowise.employeesystem.serviceimpl.EmployeeEditServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

public class EditEmployeeCommand extends EmployeeCommand {

    private static EditEmployeeCommand instance;

    private final ResponseServiceImpl responseService;

    private final EmployeeEditService employeeEditService;

    private final ObjectMapper objectMapper;

    private EditEmployeeCommand() {
        super(Set.of(RoleEnum.ROLE_ADMIN));
        this.responseService = ResponseServiceImpl.getInstance();
        this.employeeEditService = EmployeeEditServiceImpl.getInstance();
        this.objectMapper = ObjectMapperProvider.getInstance();
    }

    public static EditEmployeeCommand getInstance() {
        if (instance == null)
            instance = new EditEmployeeCommand();

        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            EmployeeDto employeeDto = objectMapper.readValue(request.getInputStream(), EmployeeDto.class);
            EmployeeDto registeredEmployee = employeeEditService.edit(employeeDto);
            responseService.processResponse(response, registeredEmployee, HttpServletResponse.SC_OK);
        } catch (IOException | ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
