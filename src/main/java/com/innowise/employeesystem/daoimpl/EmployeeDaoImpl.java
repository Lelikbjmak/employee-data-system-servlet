package com.innowise.employeesystem.daoimpl;

import com.innowise.employeesystem.config.HikariDatasource;
import com.innowise.employeesystem.dao.EmployeeDao;
import com.innowise.employeesystem.entity.Employee;
import com.innowise.employeesystem.util.EntityConstant;
import com.innowise.employeesystem.util.SqlConstant;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class EmployeeDaoImpl implements EmployeeDao {

    private static EmployeeDaoImpl instance;

    private final HikariDataSource dataSource;

    private EmployeeDaoImpl() {
        this.dataSource = HikariDatasource.getInstance();
    }

    public static EmployeeDaoImpl getInstance() {
        if (instance == null)
            instance = new EmployeeDaoImpl();

        return instance;
    }

    @Override
    public List<Employee> getAllEmployees() {

        List<Employee> foundEmployeeList = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     SqlConstant.EmployeeQuery.FIND_ALL_EMPLOYEE)) {

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = mapToEntity(resultSet);
                foundEmployeeList.add(employee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return foundEmployeeList;
    }

    @Override
    public Optional<Employee> findById(Long id) {

        Optional<Employee> optionalEmployee = Optional.empty();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     SqlConstant.EmployeeQuery.FIND_BY_ID)) {

            selectStatement.setLong(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Employee foundEmployee = mapToEntity(resultSet);
                optionalEmployee = Optional.of(foundEmployee);
            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

        return optionalEmployee;
    }

    @Override
    public Employee save(Employee employee) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement insertStatement = connection.prepareStatement(
                     SqlConstant.EmployeeQuery.INSERT, Statement.RETURN_GENERATED_KEYS)) {

            insertStatement.setString(1, employee.getFirstName());
            insertStatement.setString(2, employee.getLastName());
            insertStatement.setString(3, employee.getMiddleName());
            insertStatement.setObject(4, employee.getHireDate());

            insertStatement.executeUpdate();

            return employee;
        } catch (SQLException exception) {
            System.err.println(exception);
        }

        return employee;
    }

    @Override
    public Employee add(Employee employee, Connection connection) throws SQLException {

        try (PreparedStatement insertStatement = connection.prepareStatement(SqlConstant.EmployeeQuery.INSERT,
                Statement.RETURN_GENERATED_KEYS)) {

            insertStatement.setString(1, employee.getFirstName());
            insertStatement.setString(2, employee.getLastName());
            insertStatement.setString(3, employee.getMiddleName());
            insertStatement.setObject(4, employee.getHireDate());
            insertStatement.setLong(5, employee.getUser().getId());

            insertStatement.executeUpdate();
            ResultSet generatedKeys = insertStatement.getGeneratedKeys();

            if (generatedKeys.next())
                employee.setId(generatedKeys.getLong(EntityConstant.CommonFields.ID_FIELD));

            return employee;
        }
    }


    @Override
    public void delete(Long id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(
                     SqlConstant.EmployeeQuery.DELETE_BY_ID)) {

            deleteStatement.setLong(1, id);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<Employee> findByUsername(String username) {

        Optional<Employee> foundEmployee = Optional.empty();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     SqlConstant.EmployeeQuery.FIND_BY_USER_USERNAME)) {

            selectStatement.setString(1, username);

            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                Employee employee = mapToEntity(resultSet);
                foundEmployee = Optional.of(employee);
            }

            return foundEmployee;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private Employee mapToEntity(ResultSet resultSet) throws SQLException {
        Date sqlHireDate = (Date) resultSet.getObject(EntityConstant.Employee.HIRE_DATE_FIELD);
        return Employee.builder()
                .id(resultSet.getLong(EntityConstant.CommonFields.ID_FIELD))
                .firstName(resultSet.getString(EntityConstant.Employee.FIRST_NAME_FIELD))
                .lastName(resultSet.getString(EntityConstant.Employee.LAST_NAME_FIELD))
                .middleName(resultSet.getString(EntityConstant.Employee.MIDDLE_NAME_FIELD))
                .hireDate(sqlHireDate.toLocalDate())
                .build();
    }
}
