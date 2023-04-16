package com.innowise.employeesystem.service;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.Employee;
import com.innowise.employeesystem.exception.ServiceException;

import java.util.List;

public interface EmployeeService {

    EmployeeDto save(Employee employee) throws ServiceException;

    EmployeeDto getEmployeeById(Long id) throws ServiceException;

    void deleteEmployeeById(Long id) throws ServiceException;

    List<EmployeeDto> getAll() throws ServiceException;

    EmployeeDto getEmployeeByUserUsername(String username) throws ServiceException;
}
