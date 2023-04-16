package com.innowise.employeesystem.provider;

import com.innowise.employeesystem.dto.Response;
import lombok.AccessLevel;
import lombok.NoArgsConstructor;

import java.time.Instant;
import java.util.Map;

@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class ResponseProvider {

    private static ResponseProvider instance;

    public static ResponseProvider getInstance() {
        if (instance == null)
            instance = new ResponseProvider();

        return instance;
    }

    public Response generateResponse(String status, int code, String message, Map<String, Object> content) {
        return new Response(Instant.now(), status, code, message, content);
    }
}
