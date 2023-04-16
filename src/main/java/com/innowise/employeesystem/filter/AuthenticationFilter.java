package com.innowise.employeesystem.filter;

import com.innowise.employeesystem.dto.Response;
import com.innowise.employeesystem.provider.ResponseProvider;
import com.innowise.employeesystem.security.RedisSession;
import com.innowise.employeesystem.security.SessionStatus;
import com.innowise.employeesystem.service.RedisSessionStoreService;
import com.innowise.employeesystem.service.ResponseService;
import com.innowise.employeesystem.serviceimpl.RedisSessionStoreServiceImpl;
import com.innowise.employeesystem.serviceimpl.ResponseServiceImpl;
import com.innowise.employeesystem.util.ApiConstant;
import com.innowise.employeesystem.util.CookieUtil;
import com.innowise.employeesystem.util.MessageConstant;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Priority;
import java.io.IOException;
import java.util.Map;

@Slf4j
@Priority(4)
@WebFilter(filterName = "AuthenticationFilter", servletNames = {"EmployeeServlet"})
public class AuthenticationFilter implements Filter {

    private final ResponseService responseService = ResponseServiceImpl.getInstance();

    private final ResponseProvider responseProvider = ResponseProvider.getInstance();

    private final RedisSessionStoreService redisSessionStoreService = RedisSessionStoreServiceImpl.getInstance();

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("Processing request through AuthenticationFilter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        SessionStatus sessionStatus = SessionStatus.OK;
        HttpSession session = request.getSession(false);

        if (session != null) {
            RedisSession redisSession = redisSessionStoreService.loadSession(session.getId());
            sessionStatus = validateSession(redisSession);
        } else { // indicates that session in request is null - try to obtain session from cookie

            String cookieSessionId;
            Cookie sessionCookie = CookieUtil.retrieveCookie(request, ApiConstant.Session.SESSION_ID_COOKIE_NAME);

            if (sessionCookie == null) {
                sessionStatus = SessionStatus.NOT_FOUND;
            } else {
                cookieSessionId = sessionCookie.getValue();
                RedisSession redisSession = redisSessionStoreService.loadSession(cookieSessionId);
                sessionStatus = validateSession(redisSession);
            }
        }

        if (!sessionStatus.equals(SessionStatus.OK)) {
            Response failedAuthenticationResponse = responseProvider.generateResponse(ApiConstant.ResponseStatus.UNAUTHORIZED, 401,
                    MessageConstant.FAILED_AUTHENTICATION_MESSAGE, Map.of("session status", sessionStatus.getMessage()));
            responseService.processResponse(response, failedAuthenticationResponse, failedAuthenticationResponse.code());
        }

        filterChain.doFilter(request, response);
    }

    private SessionStatus validateSession(RedisSession redisSession) {

        if (redisSession == null)
            return SessionStatus.NOT_FOUND;

        if (!redisSession.nonExpired())
            return SessionStatus.EXPIRED;

        redisSession.updateLastAccessTime();
        redisSessionStoreService.saveSession(redisSession);

        return SessionStatus.OK;
    }
}
