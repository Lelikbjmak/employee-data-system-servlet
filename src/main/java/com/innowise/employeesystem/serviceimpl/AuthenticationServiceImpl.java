package com.innowise.employeesystem.serviceimpl;

import com.innowise.employeesystem.dto.AuthenticationRequest;
import com.innowise.employeesystem.dto.Response;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.entity.User;
import com.innowise.employeesystem.exception.ServiceException;
import com.innowise.employeesystem.mapper.UserMapper;
import com.innowise.employeesystem.provider.ResponseProvider;
import com.innowise.employeesystem.security.Argon2PasswordEncoder;
import com.innowise.employeesystem.security.AuthenticationStatus;
import com.innowise.employeesystem.security.RedisSession;
import com.innowise.employeesystem.service.AuthenticationService;
import com.innowise.employeesystem.service.RedisSessionStoreService;
import com.innowise.employeesystem.service.UserService;
import com.innowise.employeesystem.util.ApiConstant;
import com.innowise.employeesystem.util.EntityConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.mapstruct.factory.Mappers;

import java.util.Map;

public class AuthenticationServiceImpl implements AuthenticationService {

    private static AuthenticationServiceImpl instance;

    private final UserService userService;

    private final Argon2PasswordEncoder argon2PasswordEncoder;

    private final ResponseProvider authenticationResponseProvider;

    private final UserMapper userMapper = Mappers.getMapper(UserMapper.class);

    private final RedisSessionStoreService redisSessionStoreService;

    public static AuthenticationServiceImpl getInstance() {
        if (instance == null)
            instance = new AuthenticationServiceImpl();

        return instance;
    }

    private AuthenticationServiceImpl() {
        this.userService = UserServiceImpl.getInstance();
        this.argon2PasswordEncoder = Argon2PasswordEncoder.getInstance();
        this.authenticationResponseProvider = ResponseProvider.getInstance();
        this.redisSessionStoreService = RedisSessionStoreServiceImpl.getInstance();
    }

    @Override
    public Response authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request) {

        String username = authenticationRequest.username();
        String password = authenticationRequest.password();

        User foundUser;

        try {
            foundUser = userService.getByUsername(username);
        } catch (ServiceException e) {
            throw new RuntimeException(e);
        }

        if (foundUser == null) {
            return authenticationResponseProvider.generateResponse(ApiConstant.ResponseStatus.NOT_FOUND, HttpServletResponse.SC_NOT_FOUND,
                    AuthenticationStatus.USER_NOT_FOUND.getMessage(), Map.of(EntityConstant.User.USERNAME_FIELD_NAME, username));
        }

        if (!argon2PasswordEncoder.verify(foundUser.getPassword(), password)) {
            return authenticationResponseProvider.generateResponse(ApiConstant.ResponseStatus.UNAUTHORIZED, HttpServletResponse.SC_UNAUTHORIZED,
                    AuthenticationStatus.INCORRECT_PASSWORD.getMessage(), Map.of(EntityConstant.User.PASSWORD_FIELD_NAME, password));
        }

        UserDto authenticatedUser = userMapper.mapToDto(foundUser);

        HttpSession session = request.getSession(true);

        RedisSession redisSession = new RedisSession();
        redisSession.setSessionId(session.getId());
        redisSession.setSessionCredentials(authenticatedUser);
        redisSession.setLastAccessedTime(System.currentTimeMillis());

        redisSessionStoreService.saveSession(redisSession);

        return authenticationResponseProvider.generateResponse(ApiConstant.ResponseStatus.OK, HttpServletResponse.SC_OK,
                AuthenticationStatus.OK.getMessage(), null);
    }
}
