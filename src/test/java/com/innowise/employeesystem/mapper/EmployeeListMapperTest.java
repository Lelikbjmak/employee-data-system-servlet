package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.entity.Employee;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

import java.util.List;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeListMapperTest {

    private final EmployeeListMapper employeeListMapper = Mappers.getMapper(EmployeeListMapper.class);

    @Test
    @Order(1)
    @DisplayName(value = "Context loads()")
    void contextLoads() {
        Assertions.assertNotNull(employeeListMapper);
    }

    @Test
    @Order(2)
    @DisplayName("Map List<Employee> to List<EmployeeDto>")
    void mapToDtoList() {

        List<EmployeeDto> expectedEmployeeDtoList = List.of(
                EmployeeDto.builder()
                        .firstName("firstName")
                        .build(),
                EmployeeDto.builder()
                        .firstName("secondName")
                        .build()
        );

        List<EmployeeDto> actualEmployeeDtoList = employeeListMapper.mapToDto(List.of(
                Employee.builder()
                        .firstName("firstName")
                        .build(),
                Employee.builder()
                        .firstName("secondName")
                        .build()
        ));

        Assertions.assertNotNull(actualEmployeeDtoList);
        Assertions.assertEquals(expectedEmployeeDtoList, actualEmployeeDtoList);
    }
}