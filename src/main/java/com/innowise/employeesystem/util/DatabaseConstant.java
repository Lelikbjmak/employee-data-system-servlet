package com.innowise.employeesystem.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class DatabaseConstant {

    @UtilityClass
    public static class Connection {
        public static final String JDBC_URL = "jdbc:postgresql://localhost:5432/employee_data_system_servlet_api";
        public static final String USERNAME = "postgres";
        public static final String PASSWORD = "root";
        public static final String DRIVER = "org.postgresql.Driver";
        public static final int MAX_POOL_SIZE = 10;
        public static final int MIN_IDLE_CONNECTION_NUMBER = 10;
        public static final long IDLE_TIMEOUT = 60000;
        public static final long CONNECTION_POOL_TIMEOUT = 30000;
    }
}
