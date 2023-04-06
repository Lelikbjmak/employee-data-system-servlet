package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleMapperTest {

    private final RoleMapper roleMapper = Mappers.getMapper(RoleMapper.class);

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoad() {
        Assertions.assertNotNull(roleMapper);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Map String to Role entity")
    void mapToEntity() {
        Role expectedRole = Role.builder()
                .id(1)
                .name(RoleEnum.ROLE_ADMIN)
                .build();

        String roleEnum = RoleEnum.ROLE_ADMIN.name();
        Role actualRole = roleMapper.mapToEntity(roleEnum);

        Assertions.assertEquals(expectedRole, actualRole);
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map Role to String dto")
    void mapToDto() {

        Role role = Role.builder()
                .id(1)
                .name(RoleEnum.ROLE_ADMIN)
                .build();

        String expectedRole = RoleEnum.ROLE_ADMIN.name();

        String actualRole = roleMapper.mapToDto(role);

        Assertions.assertEquals(expectedRole, actualRole);
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map null to null")
    void mapToNull() {
        Assertions.assertNull(roleMapper.mapToEntity(null));
        Assertions.assertNull(roleMapper.mapToDto(null));
    }
}