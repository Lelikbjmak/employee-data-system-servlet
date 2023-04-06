package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class RoleSetMapperTest {

    private final RoleSetMapper roleSetMapper = Mappers.getMapper(RoleSetMapper.class);

    @Test
    @Order(1)
    @DisplayName(value = "Context loads")
    void contextLoads() {
        Assertions.assertNotNull(roleSetMapper);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Map Set<String> to Set<Role>")
    void mustMapStringsToRoles() {

        Set<String> stringRoles = Set.of(
                RoleEnum.ROLE_ADMIN.name(),
                RoleEnum.ROLE_USER.name()
        );

        Set<Role> expectedRoleSet = Set.of(
                Role.builder()
                        .id(1)
                        .name(RoleEnum.ROLE_ADMIN)
                        .build(),
                Role.builder()
                        .id(2)
                        .name(RoleEnum.ROLE_USER)
                        .build()
        );

        Set<Role> actualRoleSet = roleSetMapper.mapToRoleSet(stringRoles);
        Assertions.assertEquals(expectedRoleSet, actualRoleSet);
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map Set<Role> to Set<String>")
    void mustMapRolesToStrings() {

        Set<String> expectedStringRoles = Set.of(
                RoleEnum.ROLE_ADMIN.name(),
                RoleEnum.ROLE_USER.name()
        );

        Set<Role> roleSet = Set.of(
                Role.builder()
                        .id(1)
                        .name(RoleEnum.ROLE_ADMIN)
                        .build(),
                Role.builder()
                        .id(2)
                        .name(RoleEnum.ROLE_USER)
                        .build()
        );

        Set<String> actualStringSet = roleSetMapper.mapToDtoSet(roleSet);
        Assertions.assertEquals(expectedStringRoles, actualStringSet);
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map null to null")
    void mapToNull() {
        Assertions.assertNull(roleSetMapper.mapToRoleSet(null));
        Assertions.assertNull(roleSetMapper.mapToDtoSet(null));
    }
}