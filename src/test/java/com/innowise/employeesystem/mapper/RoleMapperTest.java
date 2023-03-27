package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

import java.util.Set;

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
    @DisplayName(value = "Map RoleEnum to Role entity")
    void mapToEntity() {
        Role expectedRole = Role.builder()
                .id(1)
                .name(RoleEnum.ROLE_ADMIN)
                .build();

        RoleEnum roleEnum = RoleEnum.ROLE_ADMIN;
        Role actualRole = roleMapper.mapToEntity(roleEnum);

        Assertions.assertEquals(expectedRole, actualRole);
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map Set<RoleEnum> to Set<Role>")
    void mapToEntitySet() {

        Set<Role> expectedRoleSet = Set.of(Role.builder()
                        .id(1)
                        .name(RoleEnum.ROLE_ADMIN)
                        .build(),
                Role.builder()
                        .id(2)
                        .name(RoleEnum.ROLE_USER)
                        .build());

        Set<RoleEnum> roleEnumSet = Set.of(
                RoleEnum.ROLE_ADMIN,
                RoleEnum.ROLE_USER
        );

        Set<Role> actualRoleSet = roleMapper.mapToEntitySet(roleEnumSet);
        Assertions.assertEquals(expectedRoleSet, actualRoleSet);
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map Role to RoleEnum dto")
    void mapToDto() {

        Role role = Role.builder()
                .id(1)
                .name(RoleEnum.ROLE_ADMIN)
                .build();

        RoleEnum expectedRole = RoleEnum.ROLE_ADMIN;

        RoleEnum actualRole = roleMapper.mapToDto(role);

        Assertions.assertEquals(expectedRole, actualRole);
    }

    @Test
    void mapToDtoSet() {

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

        Set<RoleEnum> expectedRoleSet = Set.of(
                RoleEnum.ROLE_ADMIN,
                RoleEnum.ROLE_USER
        );

        Set<RoleEnum> actualRoleSet = roleMapper.mapToDtoSet(roleSet);

        Assertions.assertEquals(expectedRoleSet, actualRoleSet);

    }
}