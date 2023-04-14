package com.innowise.employeesystem.command.impl;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.service.EmployeeService;
import com.innowise.employeesystem.serviceimpl.EmployeeServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.util.EntityConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Set;

public class GetEmployeeByUsernameCommand extends EmployeeCommand {

    private static GetEmployeeByUsernameCommand instance;

    private final EmployeeService employeeService;

    private final ResponseServiceImpl responseService;

    public static GetEmployeeByUsernameCommand getInstance() {
        if (instance == null)
            instance = new GetEmployeeByUsernameCommand();

        return instance;
    }

    private GetEmployeeByUsernameCommand() {
        super(Set.of(RoleEnum.ROLE_USER, RoleEnum.ROLE_ADMIN));
        this.employeeService = EmployeeServiceImpl.getInstance();
        this.responseService = ResponseServiceImpl.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String username = (String) request.getAttribute(EntityConstant.User.USERNAME_FIELD_NAME);

        try {
            EmployeeDto employeeDto = employeeService.getEmployeeByUserUsername(username);
            responseService.processResponse(response, employeeDto, HttpServletResponse.SC_OK);
        } catch (IOException | ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
