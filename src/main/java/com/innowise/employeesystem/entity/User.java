package com.innowise.employeesystem.entity;

import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Exclude
    private long id;

    private String username;

    private String password;

    private String mail;

    private Employee employee;

    private Set<Role> roleSet;

}
