package com.innowise.employeesystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.innowise.employeesystem.entity.RoleEnum;
import lombok.*;

import java.io.Serializable;
import java.util.Set;

@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
@JsonInclude(JsonInclude.Include.NON_NULL)
public class UserDto implements Serializable {

    private Long id;

    private String username;

    private String mail;

    private Set<RoleEnum> roles;
}
