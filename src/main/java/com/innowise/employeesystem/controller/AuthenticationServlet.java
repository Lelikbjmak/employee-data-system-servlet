package com.innowise.employeesystem.controller;

import com.innowise.employeesystem.service.RequestStrategyService;
import com.innowise.employeesystem.serviceimpl.RequestStrategyServiceImpl;
import com.innowise.employeesystem.util.ApiConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = ApiConstant.Routes.AUTHENTICATION_ROUTE)
public class AuthenticationServlet extends HttpServlet {

    private final RequestStrategyService requestStrategyService;


    public AuthenticationServlet() {
        this.requestStrategyService = RequestStrategyServiceImpl.getInstance();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestStrategyService.process(request, response);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestStrategyService.process(request, response);
    }
}
