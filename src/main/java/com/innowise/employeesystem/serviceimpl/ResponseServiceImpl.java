package com.innowise.employeesystem.serviceimpl;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeesystem.provider.ObjectMapperProvider;
import com.innowise.employeesystem.service.ResponseService;
import com.innowise.employeesystem.util.ApiConstant;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

public class ResponseServiceImpl implements ResponseService {

    private static ResponseServiceImpl instance;
    private final ObjectMapper objectMapper;

    private ResponseServiceImpl() {
        this.objectMapper = ObjectMapperProvider.getInstance();
    }

    public static ResponseServiceImpl getInstance() {
        if (instance == null)
            instance = new ResponseServiceImpl();

        return instance;
    }

    @Override
    public void processResponse(HttpServletResponse httpServletResponse, Object content, int status) throws IOException {
        httpServletResponse.setStatus(status);
        httpServletResponse.setContentType(ApiConstant.MediaType.APPLICATION_JSON_VALUE);
        httpServletResponse.setCharacterEncoding(ApiConstant.MediaType.CHARSET_ENCODING_VALUE);
        httpServletResponse.getOutputStream().print(objectMapper.writeValueAsString(content));
    }
}
