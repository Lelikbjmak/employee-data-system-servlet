package com.innowise.employeesystem.service;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.dto.RegistrationDto;

public interface EmployeeRegistrationService {

    EmployeeDto registerNewEmployee(RegistrationDto registrationDto);
}
