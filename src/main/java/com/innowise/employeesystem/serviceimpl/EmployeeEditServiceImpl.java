package com.innowise.employeesystem.serviceimpl;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.Employee;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.mapper.EmployeeMapper;
import com.innowise.employeesystem.service.EmployeeEditService;
import com.innowise.employeesystem.service.EmployeeService;
import com.innowise.employeesystem.service.UserService;
import org.mapstruct.factory.Mappers;

public class EmployeeEditServiceImpl implements EmployeeEditService {

    private static EmployeeEditServiceImpl instance;

    private final EmployeeService employeeService;

    private final UserService userService;

    private final EmployeeMapper employeeMapper;

    private EmployeeEditServiceImpl() {
        this.employeeService = EmployeeServiceImpl.getInstance();
        this.userService = UserServiceImpl.getInstance();
        this.employeeMapper = Mappers.getMapper(EmployeeMapper.class);
    }

    public static EmployeeEditServiceImpl getInstance() {
        if (instance == null)
            instance = new EmployeeEditServiceImpl();

        return instance;
    }

    @Override
    public EmployeeDto edit(EmployeeDto employeeDto) throws ServiceException {

        if (employeeDto == null)
            return null;

        Employee employee = employeeMapper.mapToEntity(employeeDto);

        EmployeeDto editedEmployee;

        try {
            editedEmployee = employeeService.save(employee);

            if (employee.getUser() != null) {
                UserDto editedUser = userService.save(employee.getUser());
                editedEmployee.setUserDto(editedUser);
            }

            return editedEmployee;
        } catch (ServiceException e) {
            throw new ServiceException(e);
        }
    }
}
