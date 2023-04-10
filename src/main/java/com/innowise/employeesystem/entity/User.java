package com.innowise.employeesystem.entity;

import com.innowise.employeesystem.dao.EmployeeDao;
import com.innowise.employeesystem.dao.RoleDao;
import com.innowise.employeesystem.daoimpl.EmployeeDaoImpl;
import com.innowise.employeesystem.daoimpl.RoleDaoImpl;
import lombok.*;

import java.util.Set;

@Setter
@Getter
@Builder
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @EqualsAndHashCode.Exclude
    private static final RoleDao roleDao = RoleDaoImpl.getInstance();

    @EqualsAndHashCode.Exclude
    private static final EmployeeDao employeeDao = EmployeeDaoImpl.getInstance();

    @EqualsAndHashCode.Exclude
    private long id;

    private String username;

    private String password;

    private String mail;

    private Employee employee;

    private Set<Role> roleSet;

    public Set<Role> getRoleSet() {

        if (roleSet == null)
            this.roleSet = roleDao.getUserRoles(id);


        return roleSet;
    }

    public Employee getEmployee() {

        if (employee == null) {
            this.employee = employeeDao.findById(id).orElse(null);
        }

        return employee;
    }
}
