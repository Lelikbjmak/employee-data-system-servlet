package com.innowise.employeesystem.serviceimpl;

import com.innowise.employeesystem.daoimpl.EmployeeDaoImpl;
import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.Employee;
import com.innowise.employeesystem.exception.DaoException;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.mapper.EmployeeListMapper;
import com.innowise.employeesystem.mapper.EmployeeMapper;
import com.innowise.employeesystem.service.EmployeeService;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
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
    public EmployeeDto save(Employee employee) throws ServiceException {

        if (employee == null)
            return null;

        try {
            Employee foundEmployee = employeeDao.findById(employee.getId()).orElseThrow(RuntimeException::new);
            updateEmployeeFields(employee, foundEmployee);
            Employee savedEmployee = employeeDao.save(foundEmployee);
            return employeeMapper.mapToDto(savedEmployee);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }

    }

    protected Employee register(Employee employee, Connection connection) throws ServiceException {

        if (employee == null)
            return null;

        try {
            return employeeDao.add(employee, connection);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmployeeDto getEmployeeById(Long id) throws ServiceException {

        if (id == null)
            return null;

        try {

            Optional<Employee> possibleFoundEmployee = employeeDao.findById(id);
            Employee foundEmployee = possibleFoundEmployee.orElse(null);
            return employeeMapper.mapToDto(foundEmployee);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteEmployeeById(Long id) throws ServiceException {

        if (id == null)
            return;

        try {
            employeeDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public List<EmployeeDto> getAll() throws ServiceException {
        try {
            List<Employee> foundEmployeeList = employeeDao.getAllEmployees();
            return employeeListMapper.mapToDto(foundEmployeeList);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public EmployeeDto getEmployeeByUserUsername(String username) throws ServiceException {

        if (username == null)
            return null;
        try {
            Optional<Employee> possibleFoundEmployee = employeeDao.findByUsername(username);
            Employee foundEmployee = possibleFoundEmployee.orElse(null);
            return employeeMapper.mapToDto(foundEmployee);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
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
