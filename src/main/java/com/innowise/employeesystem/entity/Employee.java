package com.innowise.employeesystem.entity;

import lombok.*;

import java.time.LocalDateTime;

@Setter
@Getter
@Builder
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @EqualsAndHashCode.Exclude
    private long id;

    private String firstName;

    private String lastName;

    private String middleName;

    private LocalDateTime hireDate;

    private User user;
}
