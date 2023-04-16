package com.innowise.employeesystem.filter;

import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.command.EmployeeCommandType;
import com.innowise.employeesystem.dto.Response;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.provider.ResponseProvider;
import com.innowise.employeesystem.security.RedisSession;
import com.innowise.employeesystem.service.RedisSessionStoreService;
import com.innowise.employeesystem.service.ResponseService;
import com.innowise.employeesystem.serviceimpl.RedisSessionStoreServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import com.innowise.employeesystem.util.ApiConstant;
import com.innowise.employeesystem.util.CommandConstant;
import com.innowise.employeesystem.util.CookieUtil;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Priority;
import java.io.IOException;

@Slf4j
@Priority(6)
@WebFilter(filterName = "AuthorizationFilter", servletNames = {"EmployeeServlet"})
public class AuthorizationFilter implements Filter {

    private final ResponseService responseService = ResponseServiceImpl.getInstance();

    private final ResponseProvider responseProvider = ResponseProvider.getInstance();

    private final RedisSessionStoreService redisSessionStoreService = RedisSessionStoreServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("Processing request through Authorization filter.");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        final String commandParameter = (String) request.getAttribute(CommandConstant.COMMAND_ATTRIBUTE);
        EmployeeCommand command = EmployeeCommandType.valueOf(commandParameter).getCommand();

        String sessionId;

        HttpSession session = request.getSession(false);

        if (session == null)
            sessionId = CookieUtil.retrieveCookie(request, ApiConstant.Session.SESSION_ID_COOKIE_NAME).getValue();
        else
            sessionId = session.getId();

        RedisSession redisSession = redisSessionStoreService.loadSession(sessionId);

        UserDto loggedUser = redisSession.getSessionCredentials();

        boolean isAuthorized = loggedUser.getRoles().stream()
                .anyMatch(roleEnum -> command.getAccessRole().contains(roleEnum));

        if (!isAuthorized) {
            Response authorizationResponse = responseProvider.generateResponse(ApiConstant.ResponseStatus.UNAUTHORIZED, HttpServletResponse.SC_UNAUTHORIZED,
                    "Access denied. Non compliant authorities.", null);
            responseService.processResponse(response, authorizationResponse, HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
