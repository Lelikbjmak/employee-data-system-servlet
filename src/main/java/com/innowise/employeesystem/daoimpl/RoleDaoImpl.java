package com.innowise.employeesystem.daoimpl;

import com.innowise.employeesystem.config.HikariDatasource;
import com.innowise.employeesystem.dao.RoleDao;
import com.innowise.employeesystem.entity.Role;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.util.EntityConstant;
import com.innowise.employeesystem.util.SqlConstant;
import com.zaxxer.hikari.HikariDataSource;
import lombok.extern.slf4j.Slf4j;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

@Slf4j
public class RoleDaoImpl implements RoleDao {

    private static RoleDaoImpl instance;

    private final HikariDataSource datasource;

    private RoleDaoImpl() {
        this.datasource = HikariDatasource.getInstance();
    }

    public static RoleDaoImpl getInstance() {
        if (instance == null) {
            instance = new RoleDaoImpl();
        }
        return instance;
    }

    @Override
    public Set<Role> getAllRoles() {

        Set<Role> foundRoleSet = new HashSet<>();

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.RoleQuery.FIND_ALL_ROLES)) {

            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Role role = mapToEntity(resultSet);
                foundRoleSet.add(role);
            }

        } catch (SQLException e) {
            log.error(e.getSQLState() + e.getMessage());
        }

        return foundRoleSet;
    }

    @Override
    public Optional<Role> findByName(RoleEnum roleEnum) {

        Optional<Role> optionalRole = Optional.empty();

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.RoleQuery.FIND_BY_NAME)) {

            statement.setString(1, roleEnum.name());
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Role foundRole = mapToEntity(resultSet);
                optionalRole = Optional.of(foundRole);
            }

        } catch (SQLException exception) {
            log.error(exception.getSQLState() + exception.getMessage());
        }

        return optionalRole;
    }

    @Override
    public Optional<Role> findById(Long id) {

        Optional<Role> optionalRole = Optional.empty();

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.RoleQuery.FIND_BY_ID)) {

            statement.setLong(1, id);
            ResultSet resultSet = statement.executeQuery();

            if (resultSet.next()) {
                Role foundRole = mapToEntity(resultSet);
                optionalRole = Optional.of(foundRole);
            }

        } catch (SQLException exception) {
            log.error(exception.getSQLState() + exception.getMessage());
        }

        return optionalRole;
    }

    @Override
    public Set<Role> getUserRoles(Long userId) {

        Set<Role> foundRoleSet = new HashSet<>();

        try (Connection connection = datasource.getConnection();
             PreparedStatement statement = connection.prepareStatement(SqlConstant.RoleQuery.FIND_ROLES_FOR_USER_BY_ID)) {

            statement.setLong(1, userId);
            ResultSet resultSet = statement.executeQuery();

            while (resultSet.next()) {
                Role role = mapToEntity(resultSet);
                foundRoleSet.add(role);
            }

        } catch (SQLException exception) {
            log.error(exception.getSQLState() + exception.getMessage());
        }

        return foundRoleSet;
    }

    private Role mapToEntity(ResultSet resultSet) throws SQLException {
        return Role.builder()
                .id(resultSet.getLong(EntityConstant.CommonFields.ID_FIELD))
                .name(RoleEnum.valueOf(resultSet.getString(EntityConstant.Role.NAME_FIELD)))
                .build();
    }
}
