package com.innowise.employeesystem.util;

import lombok.experimental.UtilityClass;

@UtilityClass
public class EntityConstant {

    @UtilityClass
    public static class CommonFields {
        public static final String ID_FIELD = "id";
    }

    @UtilityClass
    public static class Role {
        public static final String NAME_FIELD = "name";
        public static final String ROLES_FIELD_NAME = "roles";
    }

    @UtilityClass
    public static class User {
        public static final String USER_ENTITY_NAME = "user";
        public static final String USERNAME_FIELD_NAME = "username";
        public static final String MAIL_FIELD_NAME = "mail";
        public static final String PASSWORD_FIELD_NAME = "password";
        public static final String ROLE_SET_FIELD_DB_NAME = "role_set";
        public static final String ROLE_SET_FIELD_NAME = "roleSet";

    }

    @UtilityClass
    public static class Employee {
        public static final String FIRST_NAME_FIELD = "first_name";
        public static final String LAST_NAME_FIELD = "last_name";
        public static final String MIDDLE_NAME_FIELD = "middle_name";
        public static final String HIRE_DATE_FIELD = "hire_date";

    }
}
