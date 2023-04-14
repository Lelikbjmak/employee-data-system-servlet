package com.innowise.employeesystem.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.InstantDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.InstantSerializer;

import java.time.Instant;
import java.util.Map;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record Response(
        @JsonDeserialize(using = InstantDeserializer.class)
        @JsonSerialize(using = InstantSerializer.class)
        Instant timestamp,
        String status,
        int code,
        String message,
        Map<String, Object> content) {
}
