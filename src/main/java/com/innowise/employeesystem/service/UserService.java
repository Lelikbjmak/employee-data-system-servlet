package com.innowise.employeesystem.service;

import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.exception.ServiceException;

public interface UserService {

    UserDto save(User user) throws ServiceException;

    User getUserById(Long id) throws ServiceException;

    void deleteUserById(Long id) throws ServiceException;

    User getByUsername(String username) throws ServiceException;
}
