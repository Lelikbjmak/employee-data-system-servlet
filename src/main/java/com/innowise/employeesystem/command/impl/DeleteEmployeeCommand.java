package com.innowise.employeesystem.command.impl;

import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.dto.Response;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.provider.ResponseProvider;
import com.innowise.employeesystem.service.EmployeeService;
import com.innowise.employeesystem.serviceimpl.EmployeeServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import com.innowise.employeesystem.util.ApiConstant;
import com.innowise.employeesystem.util.EntityConstant;
import com.innowise.employeesystem.util.MessageConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.util.Map;
import java.util.Set;

public class DeleteEmployeeCommand extends EmployeeCommand {

    private static DeleteEmployeeCommand instance;

    private final ResponseProvider responseProvider;

    private final ResponseServiceImpl responseService;

    private final EmployeeService employeeService;

    private DeleteEmployeeCommand() {
        super(Set.of(RoleEnum.ROLE_ADMIN));
        this.employeeService = EmployeeServiceImpl.getInstance();
        this.responseService = ResponseServiceImpl.getInstance();
        this.responseProvider = ResponseProvider.getInstance();
    }

    public static DeleteEmployeeCommand getInstance() {
        if (instance == null)
            instance = new DeleteEmployeeCommand();

        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String stringId = (String) request.getAttribute(EntityConstant.CommonFields.ID_FIELD);
        Long id = Long.valueOf(stringId);

        try {
            employeeService.deleteEmployeeById(id);
            Response deletedEmployeeResponse = responseProvider.generateResponse(ApiConstant.ResponseStatus.OK, HttpServletResponse.SC_OK,
                    MessageConstant.DELETE_EMPLOYEE_MESSAGE, Map.of(EntityConstant.CommonFields.ID_FIELD, id));
            responseService.processResponse(response, deletedEmployeeResponse, HttpServletResponse.SC_OK);
        } catch (ServiceException | IOException e) {
            throw new RuntimeException(e);
        }
    }
}
