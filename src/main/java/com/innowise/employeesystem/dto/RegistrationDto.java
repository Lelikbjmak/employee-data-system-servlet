package com.innowise.employeesystem.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class RegistrationDto {

    @JsonProperty(value = "user")
    private RegistrationUserDto registrationUserDto;

    @JsonProperty(value = "employee")
    private RegistrationEmployeeDto registrationEmployeeDto;
}
