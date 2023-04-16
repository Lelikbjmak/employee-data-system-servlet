package com.innowise.employeesystem.security;

import com.innowise.employeesystem.util.PropertyUtil;
import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import static com.innowise.employeesystem.util.ApiConstant.Security.*;

public class Argon2PasswordEncoder {

    private static Argon2PasswordEncoder instance;

    private final Argon2 argon2;

    private static final int SALT_LENGTH;
    private static final int HASH_LENGTH;
    private static final int ITERATIONS;
    private static final int MEMORY;
    private static final int PARALLELISM;

    static {
        SALT_LENGTH = (int) PropertyUtil.getProperty(SALT_LENGTH_PROPERTY);
        HASH_LENGTH = (int) PropertyUtil.getProperty(HASH_LENGTH_PROPERTY);
        ITERATIONS = (int) PropertyUtil.getProperty(ITERATIONS_PROPERTY);
        MEMORY = (int) PropertyUtil.getProperty(MEMORY_PROPERTY);
        PARALLELISM = (int) PropertyUtil.getProperty(PARALLELISM_PROPERTY);
    }

    private Argon2PasswordEncoder() {
        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, SALT_LENGTH, HASH_LENGTH);
    }

    public static Argon2PasswordEncoder getInstance() {
        if (instance == null)
            instance = new Argon2PasswordEncoder();

        return instance;
    }

    public String encode(String plainPassword) {
        return argon2.hash(ITERATIONS, MEMORY, PARALLELISM, plainPassword.toCharArray());
    }

    public boolean verify(String hash, String plainPassword) {
        return argon2.verify(hash, plainPassword.toCharArray());
    }
}
