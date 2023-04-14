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

public class GetEmployeeByIdCommand extends EmployeeCommand {

    private static GetEmployeeByIdCommand instance;

    private final ResponseServiceImpl responseService;

    private final EmployeeService employeeService;

    public static GetEmployeeByIdCommand getInstance() {
        if (instance == null)
            instance = new GetEmployeeByIdCommand();

        return instance;
    }

    private GetEmployeeByIdCommand() {
        super(Set.of(RoleEnum.ROLE_USER, RoleEnum.ROLE_ADMIN));
        this.responseService = ResponseServiceImpl.getInstance();
        this.employeeService = EmployeeServiceImpl.getInstance();
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String stringId = (String) request.getAttribute(EntityConstant.CommonFields.ID_FIELD);
        Long id = Long.valueOf(stringId);

        try {
            EmployeeDto foundEmployee = employeeService.getEmployeeById(id);
            responseService.processResponse(response, foundEmployee, HttpServletResponse.SC_OK);
        } catch (IOException | ServiceException e) {
            throw new RuntimeException(e);
        }
    }
}
