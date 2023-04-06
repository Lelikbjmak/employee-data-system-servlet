package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.dto.RegistrationEmployeeDto;
import com.innowise.employeesystem.entity.Employee;
import com.innowise.employeesystem.util.DtoConstant;
import com.innowise.employeesystem.util.EntityConstant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = UserMapper.class)
public interface EmployeeMapper {

    @Mapping(target = EntityConstant.User.USER_ENTITY_NAME, ignore = true)
    Employee mapToEntity(EmployeeDto employeeDto);

    @Mapping(target = DtoConstant.UserDto.USER_DTO_NAME, source = EntityConstant.User.USER_ENTITY_NAME)
    EmployeeDto mapToDto(Employee employee);

    Employee mapToEntity(RegistrationEmployeeDto registrationEmployeeDto);
}
