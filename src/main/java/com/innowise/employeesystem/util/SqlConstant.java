package com.innowise.employeesystem.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class SqlConstant {

    @UtilityClass
    public static class RoleQuery {

        public static final String FIND_ALL_ROLES = "SELECT * FROM roles";
        public static final String FIND_BY_NAME = "SELECT * FROM roles WHERE name = ?";
        public static final String FIND_BY_ID = "SELECT * FROM roles WHERE id = ?";
        public static final String FIND_ROLES_FOR_USER_BY_ID = "SELECT R.id, R.name FROM roles R JOIN users_roles UR on UR.role_id = R.id WHERE UR.user_id = ?";
    }

    @UtilityClass
    public static class EmployeeQuery {
        public static final String INSERT = "INSERT INTO employees(first_name, last_name, middle_name, hire_date) VALUES (?, ?, ?, ?)";
        public static final String UPDATE = "UPDATE employees SET first_name = ?, last_name = ?, middle_name = ?, hire_date = ? WHERE id = ?";
        public static final String FIND_ALL = "SELECT * FROM employees";
        public static final String FIND_BY_ID = "SELECT * FROM employees WHERE id = ?";
        public static final String DELETE = "DELETE FROM employees WHERE id = ?";
        public static final String FIND_BY_USER_USERNAME = "SELECT E.id, E.first_name, E.last_name, E.middle_name, E.hire_date " +
                "FROM employees E JOIN users U on U.id = E.user_id WHERE U.username = ?";
    }

    @UtilityClass
    public static class UserQuery {
        public static final String INSERT = "INSERT INTO users VALUES (?, ?, ?, ?);";
        public static final String INSERT_USER_ROLES = "INSERT INTO users_roles VALUES (?, ?)";
        public static final String FIND_BY_ID = "SELECT * FROM users WHERE id = ?";
        public static final String FIND_BY_USERNAME = "SELECT * FROM users WHERE username = ?";
        public static final String UPDATE = "UPDATE users SET username = ?, password = ?, mail = ? WHERE id = ?";
        public static final String DELETE = "DELETE FROM users WHERE id = ?";
    }
}
