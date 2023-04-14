package com.innowise.employeesystem.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class ApiConstant {

    @UtilityClass
    public static class MediaType {
        public static final String APPLICATION_JSON_VALUE = "application/json";
        public static final String CHARSET_ENCODING_VALUE = "UTF-8";
    }

    @UtilityClass
    public static class Security {
        public static final String SALT_LENGTH_PROPERTY = "password-encoder.salt-length";
        public static final String HASH_LENGTH_PROPERTY = "password-encoder.hash_length";
        public static final String ITERATIONS_PROPERTY = "password-encoder.iterations";
        public static final String MEMORY_PROPERTY = "password-encoder.memory";
        public static final String PARALLELISM_PROPERTY = "password-encoder.parallelism";
    }

    @UtilityClass
    public static class Routes {
        public static final String API_ROUTE = "/api/1.0/employees/*";
        public static final String AUTHENTICATION_ROUTE = "/api/1.0/employees/authentication";
    }

    @UtilityClass
    public static class Method {
        public static final String GET = "GET";
        public static final String POST = "POST";
        public static final String PUT = "PUT";
        public static final String DELETE = "DELETE";
    }

    @UtilityClass
    public static class Session {
        public static final String MAX_SESSION_INACTIVE_INTERVAL_PROPERTY = "session.inactive-interval";
        public static final String SESSION_ID_COOKIE_NAME = "JSESSIONID";
    }

    @UtilityClass
    public static class ResponseStatus {
        public static final String UNAUTHORIZED = "UNAUTHORIZED";
        public static final String NOT_FOUND = "NOT_FOUND";
        public static final String OK = "OK";
        public static final String FORBIDDEN = "FORBIDDEN";
        public static final String CREATED = "CREATED";
    }

    @UtilityClass
    public static class Redis {
        public static final String HOST_PROPERTY = "redis.host";
        public static final String PORT_PROPERTY = "redis.port";
    }

}
