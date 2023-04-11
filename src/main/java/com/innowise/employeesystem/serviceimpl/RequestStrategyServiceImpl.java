package com.innowise.employeesystem.serviceimpl;

import com.innowise.employeesystem.command.EmployeeCommand;
import com.innowise.employeesystem.command.EmployeeCommandType;
import com.innowise.employeesystem.service.RequestStrategyService;
import com.innowise.employeesystem.util.CommandConstant;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class RequestStrategyServiceImpl implements RequestStrategyService {

    private static RequestStrategyServiceImpl instance;

    public static RequestStrategyServiceImpl getInstance() {
        if (instance == null)
            instance = new RequestStrategyServiceImpl();

        return instance;
    }

    @Override
    public void process(HttpServletRequest request, HttpServletResponse response) {
        String strategyParam = (String) request.getAttribute(CommandConstant.COMMAND_ATTRIBUTE);
        EmployeeCommand strategy = EmployeeCommandType.valueOf(strategyParam).getCommand();
        strategy.execute(request, response);
    }
}
