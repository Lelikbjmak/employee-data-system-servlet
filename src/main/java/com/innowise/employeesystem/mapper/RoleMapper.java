package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dao.RoleDao;
import com.innowise.employeesystem.daoimpl.RoleDaoImpl;
import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;
import org.mapstruct.Mapper;

import java.util.Set;
import java.util.stream.Collectors;

@Mapper
public interface RoleMapper {

    RoleDao roleDao = RoleDaoImpl.getInstance();

    default Role mapToEntity(RoleEnum roleEnum) {
        return roleDao.findByName(roleEnum).orElseThrow(RuntimeException::new);
    }

    default Set<Role> mapToEntitySet(Set<RoleEnum> roleDtoSet) {
        return roleDtoSet.stream()
                .map(this::mapToEntity)
                .collect(Collectors.toSet());
    }

    default RoleEnum mapToDto(Role role) {
        return role.getName();
    }

    Set<RoleEnum> mapToDtoSet(Set<Role> roleSet);
}