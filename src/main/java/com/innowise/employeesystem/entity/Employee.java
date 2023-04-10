package com.innowise.employeesystem.entity;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.innowise.employeesystem.dao.UserDao;
import com.innowise.employeesystem.daoimpl.UserDaoImpl;
import lombok.*;

import java.time.LocalDate;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class Employee {

    @EqualsAndHashCode.Exclude
    private static final UserDao USER_DAO = UserDaoImpl.getInstance();

    @EqualsAndHashCode.Exclude
    private long id;

    private String firstName;

    private String lastName;

    private String middleName;

    @JsonFormat(pattern = "yyyy-MM-dd")
    @JsonDeserialize(using = LocalDateDeserializer.class)
    @JsonSerialize(using = LocalDateSerializer.class)
    private LocalDate hireDate;

    private User user;

    public User getUser() {

        if (user == null)
            user = USER_DAO.findById(id).orElse(null);

        return user;
    }
}
