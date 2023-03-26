package com.innowise.employeesystem.config;

import com.innowise.employeesystem.util.DatabaseConstant;
import com.zaxxer.hikari.HikariConfig;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HikariConnectionPoolConfig {

    private static HikariConfig instance;

    public static synchronized HikariConfig getInstance() {
        if (instance == null)
            instance = generateHikariConfig();

        return instance;
    }

    private static HikariConfig generateHikariConfig() {

        HikariConfig config = new HikariConfig();

        config.setJdbcUrl(DatabaseConstant.Connection.JDBC_URL);
        config.setUsername(DatabaseConstant.Connection.USERNAME);
        config.setPassword(DatabaseConstant.Connection.PASSWORD);
        config.setDriverClassName(DatabaseConstant.Connection.DRIVER);

        // Set other optional properties
        config.setMaximumPoolSize(DatabaseConstant.Connection.MAX_POOL_SIZE);
        config.setMinimumIdle(DatabaseConstant.Connection.MIN_IDLE_CONNECTION_NUMBER);
        config.setIdleTimeout(DatabaseConstant.Connection.IDLE_TIMEOUT);
        config.setConnectionTimeout(DatabaseConstant.Connection.CONNECTION_POOL_TIMEOUT);

        return config;
    }

}
