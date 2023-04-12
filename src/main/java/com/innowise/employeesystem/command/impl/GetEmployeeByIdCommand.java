package com.innowise.employeesystem.command.impl;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.service.EmployeeService;
import com.innowise.employeesystem.serviceimpl.EmployeeServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import com.innowise.employeesystem.command.EmployeeCommand;
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
        String stringId = (String) request.getAttribute("id");
        Long id = Long.valueOf(stringId);
        System.err.println(id);
        EmployeeDto foundEmployee = employeeService.getEmployeeById(id);
        try {
            responseService.processResponse(response, foundEmployee, 200);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
