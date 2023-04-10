package com.innowise.employeesystem.dao;

import com.innowise.employeesystem.entity.Employee;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> getAllEmployees();

    Optional<Employee> findById(Long id);

    Employee save(Employee employee);

    Employee add(Employee employee, Connection connection) throws SQLException;

    void delete(Long id);

    Optional<Employee> findByUsername(String username);
}
