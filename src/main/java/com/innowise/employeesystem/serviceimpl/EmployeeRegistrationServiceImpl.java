package com.innowise.employeesystem.serviceimpl;

import com.innowise.employeesystem.config.HikariDatasource;
import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.dto.RegistrationDto;
import com.innowise.employeesystem.entity.Employee;
import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.mapper.EmployeeMapper;
import com.innowise.employeesystem.mapper.UserMapper;
import com.innowise.employeesystem.service.EmployeeRegistrationService;
import com.zaxxer.hikari.HikariDataSource;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
import java.sql.SQLException;

public class EmployeeRegistrationServiceImpl implements EmployeeRegistrationService {

    private static EmployeeRegistrationServiceImpl instance;

    private final UserServiceImpl userService;

    private final EmployeeServiceImpl employeeService;

    private final UserMapper userMapper;

    private final EmployeeMapper employeeMapper;

    private final HikariDataSource datasource;

    private EmployeeRegistrationServiceImpl() {
        this.userService = UserServiceImpl.getInstance();
        this.userMapper = Mappers.getMapper(UserMapper.class);
        this.employeeMapper = Mappers.getMapper(EmployeeMapper.class);
        this.datasource = HikariDatasource.getInstance();
        this.employeeService = EmployeeServiceImpl.getInstance();
    }

    public static EmployeeRegistrationServiceImpl getInstance() {
        if (instance == null)
            instance = new EmployeeRegistrationServiceImpl();

        return instance;
    }

    @Override
    public EmployeeDto registerNewEmployee(RegistrationDto registrationDto) throws ServiceException {

        User newUser = userMapper.mapToEntity(
                registrationDto.getRegistrationUserDto());

        Employee newEmployee = employeeMapper.mapToEntity(
                registrationDto.getRegistrationEmployeeDto());

        try (Connection connection = datasource.getConnection()) {

            connection.setAutoCommit(false);

            Employee employee = employeeService.register(newEmployee, connection);
            newUser.setId(employee.getId());
            User user = userService.register(newUser, connection);

            employee.setUser(user);

            connection.commit();

            return employeeMapper.mapToDto(employee);
        } catch (SQLException | ServiceException e) {
            throw new ServiceException(e);
        }
    }
}
