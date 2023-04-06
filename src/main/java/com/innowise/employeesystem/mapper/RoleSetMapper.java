package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.entity.Role;
import org.mapstruct.Mapper;

import java.util.Set;

@Mapper(uses = RoleMapper.class)
public interface RoleSetMapper {

    Set<Role> mapToRoleSet(Set<String> roleSet);

    Set<String> mapToDtoSet(Set<Role> roleSet);
}
