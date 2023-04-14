package com.innowise.employeesystem.command.impl;

import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.service.EmployeeService;
import com.innowise.employeesystem.serviceimpl.EmployeeServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.List;
import java.util.Set;

public class GetAllEmployeesCommand extends EmployeeCommand {

    private static GetAllEmployeesCommand instance;

    private final ResponseServiceImpl responseService;

    private final EmployeeService employeeService;

    private GetAllEmployeesCommand() {
        super(Set.of(RoleEnum.ROLE_USER, RoleEnum.ROLE_ADMIN));
        this.responseService = ResponseServiceImpl.getInstance();
        this.employeeService = EmployeeServiceImpl.getInstance();
    }

    public static GetAllEmployeesCommand getInstance() {
        if (instance == null)
            instance = new GetAllEmployeesCommand();

        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {
        try {
            List<EmployeeDto> foundEmployeeList = employeeService.getAll();
            responseService.processResponse(response, foundEmployeeList, HttpServletResponse.SC_OK);
        } catch (IOException | ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
