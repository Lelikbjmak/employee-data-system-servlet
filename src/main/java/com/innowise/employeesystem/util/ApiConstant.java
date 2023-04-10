package com.innowise.employeesystem.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstant {

    @UtilityClass
    public static class MediaType {
        public static final String APPLICATION_JSON_VALUE = "application/json";
    }

    @UtilityClass
    public static class Security {
        public static final int DEFAULT_SALT_LENGTH = 16;
        public static final int DEFAULT_HASH_LENGTH = 32;
        public static final int ITERATIONS = 2;
        public static final int MEMORY = 64 * 1024;
        public static final int PARALLELISM = 1;

    }

    @UtilityClass
    public static class Routes {
        public static final String API_ROUTE = "/api/1.0/employees/*";
    }
}
