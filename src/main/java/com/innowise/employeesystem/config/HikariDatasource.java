package com.innowise.employeesystem.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class HikariDatasource {

    private static HikariDataSource instance;

    public static synchronized HikariDataSource getInstance() {
        if (instance == null)
            instance = new HikariDataSource(HikariConnectionPoolConfig.getInstance());

        return instance;
    }

}
