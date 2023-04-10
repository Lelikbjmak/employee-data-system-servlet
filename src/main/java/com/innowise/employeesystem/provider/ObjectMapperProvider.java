package com.innowise.employeesystem.provider;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ObjectMapperProvider {

    private static ObjectMapper instance;

    public static synchronized ObjectMapper getInstance() {
        if (instance == null)
            instance = new ObjectMapper();

        return instance;
    }
}
