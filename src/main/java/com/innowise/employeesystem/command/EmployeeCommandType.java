package com.innowise.employeesystem.command;

import com.innowise.employeesystem.command.impl.*;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor(access = AccessLevel.PRIVATE)
public enum EmployeeCommandType {
    GET_ALL_EMPLOYEES(GetAllEmployeesCommand.getInstance()),
    GET_EMPLOYEE_BY_ID(GetEmployeeByIdCommand.getInstance()),
    GET_EMPLOYEE_BY_USERNAME(GetEmployeeByUsernameCommand.getInstance()),
    REGISTER_NEW_EMPLOYEE(RegisterNewEmployeeCommand.getInstance()),
    EDIT_EMPLOYEE(EditEmployeeCommand.getInstance()),
    AUTHENTICATE_EMPLOYEE(AuthenticateEmployeeCommand.getInstance()),
    DELETE_EMPLOYEE(DeleteEmployeeCommand.getInstance()),
    LOGOUT_EMPLOYEE(LogoutEmployeeCommand.getInstance());

    private final EmployeeCommand command;
}
