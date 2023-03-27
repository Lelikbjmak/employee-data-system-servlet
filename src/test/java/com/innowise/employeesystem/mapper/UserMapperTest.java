package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dto.RegistrationUserDto;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.util.EntityConstant;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class UserMapperTest {

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(userMapper);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Map UserDto to User entity")
    void mapToEntity() {

        UserDto userDto = UserDto.builder()
                .id(1L)
                .username(EntityConstant.User.USERNAME_FIELD)
                .mail(EntityConstant.User.MAIL_FIELD)
                .roles(Set.of(RoleEnum.ROLE_ADMIN))
                .build();

        User expectedUser = User.builder()
                .id(1L)
                .username(EntityConstant.User.USERNAME_FIELD)
                .mail(EntityConstant.User.MAIL_FIELD)
                .roleSet(Set.of(Role.builder()
                        .id(1L)
                        .name(RoleEnum.ROLE_ADMIN)
                        .build()))
                .build();

        User actualUser = userMapper.mapToEntity(userDto);

        Assertions.assertEquals(expectedUser, actualUser);
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map User to UserDto")
    void mapToDto() {

        UserDto expectedUserDto = UserDto.builder()
                .id(1L)
                .username(EntityConstant.User.USERNAME_FIELD)
                .mail(EntityConstant.User.MAIL_FIELD)
                .roles(Set.of(RoleEnum.ROLE_ADMIN))
                .build();

        User user = User.builder()
                .id(1L)
                .username(EntityConstant.User.USERNAME_FIELD)
                .mail(EntityConstant.User.MAIL_FIELD)
                .roleSet(Set.of(Role.builder()
                        .id(1L)
                        .name(RoleEnum.ROLE_ADMIN)
                        .build()))
                .build();

        UserDto actualUserDto = userMapper.mapToDto(user);

        Assertions.assertEquals(expectedUserDto, actualUserDto);
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map RegistrationUserDto to User entity")
    void testMapToEntity() {

        RegistrationUserDto userDto = RegistrationUserDto.builder()
                .username(EntityConstant.User.USERNAME_FIELD)
                .password(EntityConstant.User.PASSWORD_FIELD)
                .mail(EntityConstant.User.MAIL_FIELD)
                .roles(Set.of(RoleEnum.ROLE_ADMIN))
                .build();

        User expectedUser = User.builder()
                .username(EntityConstant.User.USERNAME_FIELD)
                .password(EntityConstant.User.PASSWORD_FIELD)
                .mail(EntityConstant.User.MAIL_FIELD)
                .roleSet(Set.of(Role.builder()
                        .id(1L)
                        .name(RoleEnum.ROLE_ADMIN)
                        .build()))
                .build();

        User actualUser = userMapper.mapToEntity(userDto);
        Assertions.assertEquals(expectedUser, actualUser);
    }
}