package com.innowise.employeesystem.mapper;

import com.innowise.employeesystem.dto.EmployeeDto;
import com.innowise.employeesystem.dto.RegistrationEmployeeDto;
import com.innowise.employeesystem.entity.Employee;
import org.junit.jupiter.api.*;
import org.mapstruct.factory.Mappers;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class EmployeeMapperTest {

    private final EmployeeMapper employeeMapper = Mappers.getMapper(EmployeeMapper.class);

    @Test
    @Order(1)
    @DisplayName(value = "Context loads()")
    void contextLoads() {
        Assertions.assertNotNull(employeeMapper);
    }

    @Test
    @Order(2)
    @DisplayName(value = "Map Entity to Dto")
    void mapEntityToDto() {

        String firstName = "firstName";

        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName(firstName)
                .build();

        Employee employee = Employee.builder()
                .firstName(firstName)
                .build();

        EmployeeDto actualDto = employeeMapper.mapToDto(employee);
        Assertions.assertNotNull(actualDto);
        Assertions.assertEquals(firstName, actualDto.getFirstName());
    }

    @Test
    @Order(3)
    @DisplayName(value = "Map EmployeeDto to Entity")
    void mapDtoToEntity() {

        String firstName = "firstName";

        EmployeeDto employeeDto = EmployeeDto.builder()
                .firstName(firstName)
                .build();

        Employee employee = Employee.builder()
                .firstName(firstName)
                .build();

        Employee actualEmployee = employeeMapper.mapToEntity(employeeDto);
        Assertions.assertNotNull(actualEmployee);
        Assertions.assertEquals(firstName, actualEmployee.getFirstName());
    }

    @Test
    @Order(4)
    @DisplayName(value = "Map RegistrationEmployeeDto to Entity")
    void mapRegisterDtoToEntity() {

        String firstName = "firstName";

        RegistrationEmployeeDto employeeDto = RegistrationEmployeeDto.builder()
                .firstName(firstName)
                .build();

        Employee employee = Employee.builder()
                .firstName(firstName)
                .build();

        Employee actualEmployee = employeeMapper.mapToEntity(employeeDto);
        Assertions.assertNotNull(actualEmployee);
        Assertions.assertEquals(firstName, actualEmployee.getFirstName());
    }

    @Test
    @Order(5)
    @DisplayName(value = "Map null to null")
    void mapToNull() {
        RegistrationEmployeeDto registrationEmployeeDto = null;
        EmployeeDto employeeDto = null;
        Assertions.assertNull(employeeMapper.mapToEntity(employeeDto));
        Assertions.assertNull(employeeMapper.mapToEntity(registrationEmployeeDto));
        Assertions.assertNull(employeeMapper.mapToDto(null));
    }
}