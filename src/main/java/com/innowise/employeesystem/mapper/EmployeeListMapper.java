package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.Employee;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(uses = EmployeeMapper.class)
public interface EmployeeListMapper {

    List<EmployeeDto> mapToDto(List<Employee> employeeList);
}
