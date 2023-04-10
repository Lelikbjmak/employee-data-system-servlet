package com.innowise.employeesystem.dao;

import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;

import java.util.Optional;
import java.util.Set;

public interface RoleDao {

    Set<Role> getAllRoles();

    Optional<Role> findByName(RoleEnum roleEnum);

    Optional<Role> findById(Long id);

    Set<Role> getUserRoles(Long userId);
}
