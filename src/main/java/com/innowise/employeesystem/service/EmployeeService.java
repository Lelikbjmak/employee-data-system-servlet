package com.innowise.employeesystem.service;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.Employee;

import java.util.List;

public interface EmployeeService {

    EmployeeDto save(Employee employee);

    EmployeeDto getEmployeeById(Long id);

    void deleteEmployeeById(Long id);

    List<EmployeeDto> getAll();

    EmployeeDto getEmployeeByUserUsername(String username);
}
