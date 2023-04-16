package com.innowise.employeesystem.service;

import com.innowise.employeesystem.security.RedisSession;

public interface RedisSessionStoreService {

    void saveSession(RedisSession redisSession);

    RedisSession loadSession(String sessionId);

    void deleteSession(String sessionId);
}
