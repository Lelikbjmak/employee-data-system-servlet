package com.innowise.employeesystem.util;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.experimental.UtilityClass;

import java.util.Arrays;

@UtilityClass
public class CookieUtil {

    public static Cookie retrieveCookie(HttpServletRequest request, String cookieName) {

        if (request.getCookies() == null)
            return null;

        return Arrays.stream(request.getCookies())
                .filter(cookie -> cookie.getName().equals(cookieName))
                .findFirst().orElse(null);
    }
}
