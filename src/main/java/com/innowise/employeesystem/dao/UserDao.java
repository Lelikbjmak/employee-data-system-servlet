package com.innowise.employeesystem.dao;

import com.innowise.employeesystem.entity.User;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Optional;

public interface UserDao {

    User add(User user, Connection connection) throws SQLException;

    Optional<User> findById(Long id);

    Optional<User> findByUsername(String username);

    User save(User user);

    void delete(Long id);

}
