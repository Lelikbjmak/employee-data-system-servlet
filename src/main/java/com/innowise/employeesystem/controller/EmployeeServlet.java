package com.innowise.employeesystem.controller;

import com.innowise.employeesystem.service.RequestStrategyService;
import com.innowise.employeesystem.serviceimpl.RequestStrategyServiceImpl;
import com.innowise.employeesystem.util.ApiConstant;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

@Slf4j
@WebServlet(name = "EmployeeServlet", urlPatterns = ApiConstant.Routes.API_ROUTE)
public class EmployeeServlet extends HttpServlet {

    private final RequestStrategyService requestStrategyService;

    public EmployeeServlet() {
        this.requestStrategyService = RequestStrategyServiceImpl.getInstance();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestStrategyService.process(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestStrategyService.process(request, response);
    }

    @Override
    protected void doPut(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestStrategyService.process(request, response);
    }

    @Override
    protected void doDelete(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        requestStrategyService.process(request, response);
    }
}
