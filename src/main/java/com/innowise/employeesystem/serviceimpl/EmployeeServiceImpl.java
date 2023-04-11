package com.innowise.employeesystem.serviceimpl;

import com.innowise.employeesystem.daoimpl.EmployeeDaoImpl;
import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.Employee;
import com.innowise.employeesystem.mapper.EmployeeListMapper;
import com.innowise.employeesystem.mapper.EmployeeMapper;
import com.innowise.employeesystem.service.EmployeeService;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;

public class EmployeeServiceImpl implements EmployeeService {

    private static EmployeeServiceImpl instance;

    private final EmployeeDaoImpl employeeDao;

    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    private final EmployeeListMapper employeeListMapper = Mappers.getMapper(EmployeeListMapper.class);

    private EmployeeServiceImpl() {
        this.employeeDao = EmployeeDaoImpl.getInstance();
    }

    public static EmployeeServiceImpl getInstance() {
        if (instance == null)
            instance = new EmployeeServiceImpl();

        return instance;
    }

    @Override
    public EmployeeDto save(Employee employee) {
        if (employee == null)
            return null;
        Employee foundEmployee = employeeDao.findById(employee.getId()).orElseThrow(RuntimeException::new);
        updateEmployeeFields(employee, foundEmployee);
        Employee savedEmployee = employeeDao.save(foundEmployee);
        return employeeMapper.mapToDto(savedEmployee);
    }

    protected Employee register(Employee employee, Connection connection) throws SQLException {
        if (employee == null)
            return null;

        return employeeDao.add(employee, connection);
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) {
        if (id == null)
            return null;

        Optional<Employee> possibleFoundEmployee = employeeDao.findById(id);
        Employee foundEmployee = possibleFoundEmployee.orElse(null);
        return employeeMapper.mapToDto(foundEmployee);
    }

    @Override
    public void deleteEmployeeById(Long id) {
        if (id == null)
            return;

        employeeDao.delete(id);
    }

    @Override
    public List<EmployeeDto> getAll() {
        List<Employee> foundEmployeeList = employeeDao.getAllEmployees();
        return employeeListMapper.mapToDto(foundEmployeeList);
    }

    @Override
    public EmployeeDto getEmployeeByUserUsername(String username) {
        if (username == null)
            return null;

        Optional<Employee> possibleFoundEmployee = employeeDao.findByUsername(username);
        Employee foundEmployee = possibleFoundEmployee.orElse(null);
        return employeeMapper.mapToDto(foundEmployee);
    }

    private void updateEmployeeFields(Employee editedEmployee, Employee foundEmployee) {

        if (editedEmployee.getFirstName() != null)
            foundEmployee.setFirstName(editedEmployee.getFirstName());

        if (editedEmployee.getLastName() != null)
            foundEmployee.setLastName(editedEmployee.getLastName());

        if (editedEmployee.getMiddleName() != null)
            foundEmployee.setMiddleName(editedEmployee.getMiddleName());
    }
}
