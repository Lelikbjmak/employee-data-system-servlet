package com.innowise.employeesystem.filter;

import com.innowise.employeesystem.command.EmployeeCommandType;
import com.innowise.employeesystem.util.ApiConstant;
import com.innowise.employeesystem.util.CommandConstant;
import com.innowise.employeesystem.util.EntityConstant;
import jakarta.servlet.*;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import javax.annotation.Priority;
import java.io.IOException;

@Slf4j
@Priority(2)
@WebFilter(filterName = "CommandFilter", urlPatterns = {ApiConstant.Routes.API_ROUTE})
public class CommandFilter implements Filter {

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {

        log.info("Command filter");

        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse) servletResponse;

        if (!request.getMethod().equals(ApiConstant.Method.GET)) {
            processNotGetRequest(request);
            filterChain.doFilter(request, response);
            return;
        }

        String additionalRouteComponents = request.getPathInfo();

        if (request.getServletPath().contains("/authentication")) {
            request.setAttribute(CommandConstant.COMMAND_ATTRIBUTE, EmployeeCommandType.LOGOUT_EMPLOYEE.name());
            filterChain.doFilter(request, response);
        } else if (additionalRouteComponents == null) {
            request.setAttribute(CommandConstant.COMMAND_ATTRIBUTE, EmployeeCommandType.GET_ALL_EMPLOYEES.name());
            filterChain.doFilter(request, response);
        } else if (additionalRouteComponents.matches("/\\d+")) {
            request.setAttribute(CommandConstant.COMMAND_ATTRIBUTE, EmployeeCommandType.GET_EMPLOYEE_BY_ID.name());
            request.setAttribute(EntityConstant.CommonFields.ID_FIELD, additionalRouteComponents.split("/")[1]);
            filterChain.doFilter(request, response);
        } else if (additionalRouteComponents.matches("/\\w+")) {
            request.setAttribute(CommandConstant.COMMAND_ATTRIBUTE, EmployeeCommandType.GET_EMPLOYEE_BY_USERNAME.name());
            request.setAttribute(EntityConstant.User.USERNAME_FIELD_NAME, additionalRouteComponents.split("/")[1]);
            filterChain.doFilter(request, response);
        } else {
            log.error("No mapping was found for: " + request.getServletPath());
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        }

    }

    private void processNotGetRequest(HttpServletRequest request) {

        if (request.getMethod().equals(ApiConstant.Method.POST) && request.getServletPath().contains("/authentication"))
            request.setAttribute(CommandConstant.COMMAND_ATTRIBUTE, EmployeeCommandType.AUTHENTICATE_EMPLOYEE.name());
        else
            request.setAttribute(CommandConstant.COMMAND_ATTRIBUTE, EmployeeCommandType.REGISTER_NEW_EMPLOYEE.name());

        if (request.getMethod().equals(ApiConstant.Method.PUT))
            request.setAttribute(CommandConstant.COMMAND_ATTRIBUTE, EmployeeCommandType.EDIT_EMPLOYEE.name());

        if (request.getMethod().equals(ApiConstant.Method.DELETE))
            request.setAttribute(CommandConstant.COMMAND_ATTRIBUTE, EmployeeCommandType.DELETE_EMPLOYEE.name());
    }
}
