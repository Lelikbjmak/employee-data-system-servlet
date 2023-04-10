package com.innowise.employeesystem.service;

import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.User;

public interface UserService {

    UserDto save(User user);

    User getUserById(Long id);

    void deleteUserById(Long id);

    User getByUsername(String username);
}
