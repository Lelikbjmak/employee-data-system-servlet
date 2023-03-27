package com.innowise.employeesystem.dto;

import com.innowise.employeesystem.entity.RoleEnum;
import lombok.*;

import java.util.Set;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class UserDto {

    private Long id;

    private String username;

    private String mail;

    private Set<RoleEnum> roles;
}
