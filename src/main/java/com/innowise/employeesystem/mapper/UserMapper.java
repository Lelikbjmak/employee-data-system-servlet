package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dto.RegistrationUserDto;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.util.DtoConstant;
import com.innowise.employeesystem.util.EntityConstant;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(uses = RoleMapper.class)
public interface UserMapper {

    @Mapping(source = DtoConstant.UserDto.ROLE_SET_FILED_NAME, target = EntityConstant.User.ROLE_SET_FIELD_NAME)
    User mapToEntity(UserDto userDto);

    @Mapping(source = EntityConstant.User.ROLE_SET_FIELD_NAME, target = DtoConstant.UserDto.ROLE_SET_FILED_NAME)
    UserDto mapToDto(User user);

    @Mapping(source = DtoConstant.UserDto.ROLE_SET_FILED_NAME, target = EntityConstant.User.ROLE_SET_FIELD_NAME)
    User mapToEntity(RegistrationUserDto registrationUserDto);

}
