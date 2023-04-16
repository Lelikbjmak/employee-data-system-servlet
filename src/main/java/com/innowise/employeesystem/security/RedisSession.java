package com.innowise.employeesystem.security;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.innowise.employeesystem.dto.UserDto;
import com.innowise.employeesystem.util.ApiConstant;
import com.innowise.employeesystem.util.PropertyUtil;
import lombok.*;

import java.io.Serializable;

@Setter
@Getter
@Builder
@ToString
@NoArgsConstructor
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class RedisSession implements Serializable {

    private static final long MAX_SESSION_INACTIVE_INTERVAL;

    private String sessionId;

    private UserDto sessionCredentials;

    private long lastAccessedTime;

    static {
        MAX_SESSION_INACTIVE_INTERVAL = (int) PropertyUtil.getProperty(ApiConstant.Session.MAX_SESSION_INACTIVE_INTERVAL_PROPERTY);
    }

    public boolean nonExpired() {
        return (lastAccessedTime + MAX_SESSION_INACTIVE_INTERVAL) > System.currentTimeMillis();
    }

    public void updateLastAccessTime() {
        this.lastAccessedTime = System.currentTimeMillis();
    }
}
