package com.innowise.employeesystem.entity;


import lombok.*;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Role {

    private long id;

    private RoleEnum name;

}
