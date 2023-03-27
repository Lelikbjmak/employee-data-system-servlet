package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dto.RegistrationUserDto;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(source = "roles", target = "roleSet")
    User mapToEntity(UserDto userDto);

    @Mapping(source = "roleSet", target = "roles")
    UserDto mapToDto(User user);

    @Mapping(source = "roles", target = "roleSet")
    User mapToEntity(RegistrationUserDto registrationUserDto);

}
