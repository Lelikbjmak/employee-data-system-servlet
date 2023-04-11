package com.innowise.employeesystem.serviceimpl;

import com.innowise.employeesystem.daoimpl.UserDaoImpl;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.mapper.UserMapper;
import com.innowise.employeesystem.security.Argon2PasswordEncoder;
import com.innowise.employeesystem.service.UserService;
import org.mapstruct.factory.Mappers;

import java.sql.Connection;
import java.sql.SQLException;
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

    protected User register(User user, Connection connection) throws SQLException {
        hashPassword(user);
        return userDao.add(user, connection);
    }

    @Override
    public UserDto save(User user) {
        User foundUser = userDao.findById(user.getId()).orElseThrow(RuntimeException::new);
        updateUserFields(user, foundUser);
        User savedUser = userDao.save(user);
        return userMapper.mapToDto(savedUser);
    }

    @Override
    public User getUserById(Long id) {
        Optional<User> userOptional = userDao.findById(id);
        return userOptional.orElse(null);
    }

    @Override
    public void deleteUserById(Long id) {
        userDao.delete(id);
    }

    @Override
    public User getByUsername(String username) {
        Optional<User> optionalUser = userDao.findByUsername(username);
        return optionalUser.orElse(null);
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
