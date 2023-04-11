package com.innowise.employeesystem.service;

import com.innowise.employeesystem.dto.AuthenticationRequest;
import com.innowise.employeesystem.dto.Response;
import jakarta.servlet.http.HttpServletRequest;

public interface AuthenticationService {

    Response authenticate(AuthenticationRequest authenticationRequest, HttpServletRequest request);

}
