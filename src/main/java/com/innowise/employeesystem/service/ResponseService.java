package com.innowise.employeesystem.service;

import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public interface ResponseService {

    void processResponse(HttpServletResponse httpServletResponse, Object content, int status) throws IOException;
}
