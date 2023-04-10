package com.innowise.employeesystem.security;

import de.mkammerer.argon2.Argon2;
import de.mkammerer.argon2.Argon2Factory;

import static com.innowise.employeesystem.util.ApiConstant.Security.*;

public class Argon2PasswordEncoder {

    private static Argon2PasswordEncoder instance;

    private final Argon2 argon2;

    private Argon2PasswordEncoder() {
        argon2 = Argon2Factory.create(Argon2Factory.Argon2Types.ARGON2i, DEFAULT_SALT_LENGTH, DEFAULT_HASH_LENGTH);
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
