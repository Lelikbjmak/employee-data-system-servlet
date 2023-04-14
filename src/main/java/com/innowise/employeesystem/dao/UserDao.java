package com.innowise.employeesystem.dao;

import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.exception.DaoException;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {

    User add(User user, Connection connection) throws SQLException, DaoException;

    Optional<User> findById(Long id) throws DaoException;

    Optional<User> findByUsername(String username) throws DaoException;

    User save(User user) throws DaoException;

    void delete(Long id) throws DaoException;

}
