package com.innowise.employeesystem.command.impl;

import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.dto.Response;
import com.innowise.employeesystem.entity.RoleEnum;
import com.innowise.employeesystem.provider.ResponseProvider;
import com.innowise.employeesystem.service.RedisSessionStoreService;
import com.innowise.employeesystem.service.ResponseService;
import com.innowise.employeesystem.serviceimpl.RedisSessionStoreServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import com.innowise.employeesystem.util.ApiConstant;
import com.innowise.employeesystem.util.CookieUtil;
import com.innowise.employeesystem.util.MessageConstant;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

import java.io.IOException;
import java.util.Set;

public class LogoutEmployeeCommand extends EmployeeCommand {

    private static LogoutEmployeeCommand instance;

    private final ResponseProvider responseProvider;

    private final ResponseService responseService;

    private final RedisSessionStoreService redisSessionStoreService;

    private LogoutEmployeeCommand() {
        super(Set.of(RoleEnum.ROLE_USER, RoleEnum.ROLE_ADMIN));
        this.responseProvider = ResponseProvider.getInstance();
        this.responseService = ResponseServiceImpl.getInstance();
        this.redisSessionStoreService = RedisSessionStoreServiceImpl.getInstance();
    }

    public static LogoutEmployeeCommand getInstance() {
        if (instance == null)
            instance = new LogoutEmployeeCommand();

        return instance;
    }

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response) {

        String sessionId = null;

        HttpSession session = request.getSession(false);
        Cookie sessionCookie = CookieUtil.retrieveCookie(request, ApiConstant.Session.SESSION_ID_COOKIE_NAME);

        if (session != null) {
            sessionId = session.getId();
            session.invalidate();
        }

        if (sessionCookie != null) {
            sessionCookie.setMaxAge(0);
            response.addCookie(sessionCookie);
            sessionId = sessionCookie.getValue();
        }

        redisSessionStoreService.deleteSession(sessionId);

        try {
            Response logoutResponse = responseProvider.generateResponse(ApiConstant.ResponseStatus.OK, HttpServletResponse.SC_OK, MessageConstant.LOGOUT_MESSAGE, null);
            responseService.processResponse(response, logoutResponse, HttpServletResponse.SC_OK);
        } catch (
                IOException e) {
            throw new RuntimeException(e);
        }
    }
}
