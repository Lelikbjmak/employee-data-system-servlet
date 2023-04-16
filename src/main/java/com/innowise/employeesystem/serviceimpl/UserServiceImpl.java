package com.innowise.employeesystem.serviceimpl;

import com.innowise.employeesystem.daoimpl.UserDaoImpl;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.exception.DaoException;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.mapper.UserMapper;
import com.innowise.employeesystem.security.Argon2PasswordEncoder;
import com.innowise.employeesystem.service.UserService;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
import java.util.Optional;

public class UserServiceImpl implements UserService {

    private static UserServiceImpl instance;

    private final UserDaoImpl userDao;

    private final Argon2PasswordEncoder argon2PasswordEncoder;

    private final UserMapper userMapper;

    private UserServiceImpl() {
        this.userDao = UserDaoImpl.getInstance();
        this.argon2PasswordEncoder = Argon2PasswordEncoder.getInstance();
        this.userMapper = Mappers.getMapper(UserMapper.class);
    }

    public static UserServiceImpl getInstance() {
        if (instance == null)
            instance = new UserServiceImpl();

        return instance;
    }

    protected User register(User user, Connection connection) throws ServiceException {

        hashPassword(user);

        try {
            return userDao.add(user, connection);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public UserDto save(User user) throws ServiceException {

        User foundUser;

        try {
            foundUser = userDao.findById(user.getId()).orElseThrow(RuntimeException::new);
            updateUserFields(user, foundUser);
            User savedUser = userDao.save(user);
            return userMapper.mapToDto(savedUser);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getUserById(Long id) throws ServiceException {
        try {
            Optional<User> userOptional = userDao.findById(id);
            return userOptional.orElse(null);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public void deleteUserById(Long id) throws ServiceException {
        try {
            userDao.delete(id);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    @Override
    public User getByUsername(String username) throws ServiceException {
        try {
            Optional<User> optionalUser = userDao.findByUsername(username);
            return optionalUser.orElse(null);
        } catch (DaoException e) {
            throw new ServiceException(e);
        }
    }

    private void hashPassword(User user) {
        user.setPassword(argon2PasswordEncoder.encode(user.getPassword()));
    }

    private void updateUserFields(User editedUser, User foundUser) {

        if (editedUser.getUsername() == null)
            editedUser.setUsername(foundUser.getUsername());

        if (editedUser.getPassword() == null)
            editedUser.setPassword(foundUser.getPassword());
        else
            hashPassword(editedUser);

        if (editedUser.getMail() == null)
            editedUser.setMail(foundUser.getMail());
    }
}
