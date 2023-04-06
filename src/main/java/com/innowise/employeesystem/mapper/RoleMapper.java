package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dao.RoleDao;
import com.innowise.employeesystem.daoimpl.RoleDaoImpl;
import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.exception.RoleNotFoundException;
import com.innowise.employeesystem.util.EntityConstant;
import com.innowise.employeesystem.util.MessageConstant;
import org.mapstruct.Mapper;

import java.time.Instant;
import java.util.Map;

@Mapper
public interface RoleMapper {

    RoleDao roleDao = RoleDaoImpl.getInstance();

    default Role mapToEntity(String roleString) {

        if (roleString == null)
            return null;

        try {
            RoleEnum roleEnum = RoleEnum.valueOf(roleString);
            return roleDao.findByName(roleEnum).orElseThrow(() -> new RoleNotFoundException(MessageConstant.RoleMessage.ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE,
                    Instant.now(), Map.of(EntityConstant.Role.NAME_FIELD, roleString)));
        } catch (IllegalArgumentException exception) {
            throw new RoleNotFoundException(MessageConstant.RoleMessage.ROLE_IS_NOT_FOUND_EXCEPTION_MESSAGE,
                    Instant.now(), Map.of(EntityConstant.Role.NAME_FIELD, roleString));
        }
    }

    default String mapToDto(Role role) {
        if (role == null)
            return null;

        return role.getName().name();
    }
}