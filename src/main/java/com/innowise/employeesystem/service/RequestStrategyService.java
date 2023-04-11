package com.innowise.employeesystem.service;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public interface RequestStrategyService {

    void process(HttpServletRequest request, HttpServletResponse response);
}
