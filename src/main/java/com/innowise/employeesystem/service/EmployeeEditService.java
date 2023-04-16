package com.innowise.employeesystem.service;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.exception.ServiceException;

public interface EmployeeEditService {

    EmployeeDto edit(EmployeeDto employee) throws ServiceException;

}
