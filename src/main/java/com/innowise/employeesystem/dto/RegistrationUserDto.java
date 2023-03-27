package com.innowise.employeesystem.dto;

import com.innowise.employeesystem.entity.RoleEnum;
import lombok.*;

import java.util.Set;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationUserDto {

    private String username;

    private String password;

    private String mail;

    private Set<RoleEnum> roles;
}
