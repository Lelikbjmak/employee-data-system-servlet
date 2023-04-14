package com.innowise.employeesystem.dao;

import com.innowise.employeesystem.entity.Employee;
import com.innowise.employeesystem.exception.DaoException;

import java.sql.Connection;
import java.util.List;
import java.util.Optional;

public interface EmployeeDao {

    List<Employee> getAllEmployees() throws DaoException;

    Optional<Employee> findById(Long id) throws DaoException;

    Employee save(Employee employee) throws DaoException;

    Employee add(Employee employee, Connection connection) throws DaoException;

    void delete(Long id) throws DaoException;

    Optional<Employee> findByUsername(String username) throws DaoException;
}
