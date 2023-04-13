package com.innowise.employeesystem.command;

import com.innowise.employeesystem.entity.RoleEnum;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Set;

@Getter
@RequiredArgsConstructor
public abstract class EmployeeCommand {

    private final Set<RoleEnum> accessRole;

    public abstract void execute(HttpServletRequest request, HttpServletResponse response);

}
