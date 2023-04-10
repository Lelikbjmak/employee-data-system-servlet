package com.innowise.employeesystem.daoimpl;

import com.innowise.employeesystem.config.HikariDatasource;
import com.innowise.employeesystem.dao.UserDao;
import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.util.EntityConstant;
import com.innowise.employeesystem.util.SqlConstant;
import com.zaxxer.hikari.HikariDataSource;

import java.sql.*;
import java.util.Optional;

public class UserDaoImpl implements UserDao {

    private static UserDaoImpl instance;

    private final HikariDataSource dataSource;

    private UserDaoImpl() {
        this.dataSource = HikariDatasource.getInstance();
    }

    public static UserDaoImpl getInstance() {
        if (instance == null)
            instance = new UserDaoImpl();

        return instance;
    }

    @Override
    public User add(User user, Connection connection) throws SQLException {

        try (PreparedStatement userInsertStatement = connection.prepareStatement(SqlConstant.UserQuery.INSERT, Statement.RETURN_GENERATED_KEYS);
             PreparedStatement userRolesInsertStatement = connection.prepareStatement(SqlConstant.UserQuery.INSERT_USER_ROLES, Statement.RETURN_GENERATED_KEYS)) {

            userInsertStatement.setLong(1, user.getId());
            userInsertStatement.setString(2, user.getUsername());
            userInsertStatement.setString(3, user.getPassword());
            userInsertStatement.setString(4, user.getMail());

            userInsertStatement.executeUpdate();

            ResultSet generatedKeys = userInsertStatement.getGeneratedKeys();

            if (generatedKeys.next())
                user.setId(generatedKeys.getLong(EntityConstant.CommonFields.ID_FIELD));

            for (Role role :
                    user.getRoleSet()) {
                userRolesInsertStatement.setLong(1, user.getId());
                userRolesInsertStatement.setLong(2, role.getId());
                userRolesInsertStatement.executeUpdate();
            }

            return user;
        }
    }

    @Override
    public Optional<User> findById(Long id) {

        Optional<User> optionalUser = Optional.empty();
        User foundUser;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     SqlConstant.UserQuery.FIND_BY_ID)) {

            selectStatement.setLong(1, id);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                foundUser = mapToUser(resultSet);
                optionalUser = Optional.of(foundUser);
            }

            return optionalUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {

        Optional<User> optionalUser = Optional.empty();
        User foundUser;

        try (Connection connection = dataSource.getConnection();
             PreparedStatement selectStatement = connection.prepareStatement(
                     SqlConstant.UserQuery.FIND_BY_USERNAME)) {

            selectStatement.setString(1, username);
            ResultSet resultSet = selectStatement.executeQuery();

            while (resultSet.next()) {
                foundUser = mapToUser(resultSet);
                optionalUser = Optional.of(foundUser);
            }

            return optionalUser;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public User save(User user) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement saveStatement = connection.prepareStatement(
                     SqlConstant.UserQuery.UPDATE)) {

            saveStatement.setString(1, user.getUsername()); // username
            saveStatement.setString(2, user.getPassword()); // password
            saveStatement.setString(3, user.getMail()); // mail
            saveStatement.setLong(4, user.getId()); // where id = ?

            saveStatement.executeUpdate();

            return user;
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void delete(Long id) {

        try (Connection connection = dataSource.getConnection();
             PreparedStatement deleteStatement = connection.prepareStatement(
                     SqlConstant.UserQuery.DELETE)) {

            deleteStatement.setLong(1, id);
            deleteStatement.executeUpdate();

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private User mapToUser(ResultSet resultSet) throws SQLException {
        return User.builder()
                .id(resultSet.getLong(EntityConstant.CommonFields.ID_FIELD))
                .username(resultSet.getString(EntityConstant.User.USERNAME_FIELD_NAME))
                .password(resultSet.getString(EntityConstant.User.PASSWORD_FIELD_NAME))
                .mail(resultSet.getString(EntityConstant.User.MAIL_FIELD_NAME))
                .build();
    }
}
