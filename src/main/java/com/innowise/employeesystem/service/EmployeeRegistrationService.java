package com.innowise.employeesystem.service;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.dto.RegistrationDto;
import com.innowise.employeesystem.exception.ServiceException;

public interface EmployeeRegistrationService {

    EmployeeDto registerNewEmployee(RegistrationDto registrationDto) throws ServiceException;
}
