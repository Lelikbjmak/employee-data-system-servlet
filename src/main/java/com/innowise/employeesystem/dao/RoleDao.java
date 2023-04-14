package com.innowise.employeesystem.dao;

import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.exception.DaoException;

import java.util.Optional;
import java.util.Set;

public interface RoleDao {

    Set<Role> getAllRoles() throws DaoException;

    Optional<Role> findByName(RoleEnum roleEnum) throws DaoException;

    Optional<Role> findById(Long id) throws DaoException;

    Set<Role> getUserRoles(Long userId) throws DaoException;
}
