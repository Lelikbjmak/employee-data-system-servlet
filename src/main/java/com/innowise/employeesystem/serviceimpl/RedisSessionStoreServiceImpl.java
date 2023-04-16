package com.innowise.employeesystem.serviceimpl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.innowise.employeesystem.provider.ObjectMapperProvider;
import com.innowise.employeesystem.security.RedisSession;
import com.innowise.employeesystem.service.RedisSessionStoreService;
import com.innowise.employeesystem.util.ApiConstant;
import com.innowise.employeesystem.util.PropertyUtil;
import redis.clients.jedis.Jedis;

public class RedisSessionStoreServiceImpl implements RedisSessionStoreService {

    private final Jedis jedis;

    private static RedisSessionStoreServiceImpl instance;

    private final ObjectMapper objectMapper;

    private static final String REDIS_HOST;
    private static final int REDIS_PORT;

    static {
        REDIS_HOST = (String) PropertyUtil.getProperty(ApiConstant.Redis.HOST_PROPERTY);
        REDIS_PORT = (int) PropertyUtil.getProperty(ApiConstant.Redis.PORT_PROPERTY);
    }

    private RedisSessionStoreServiceImpl() {
        jedis = new Jedis(REDIS_HOST, REDIS_PORT);
        this.objectMapper = ObjectMapperProvider.getInstance();
    }

    public static RedisSessionStoreServiceImpl getInstance() {
        if (instance == null)
            instance = new RedisSessionStoreServiceImpl();

        return instance;
    }

    @Override
    public void saveSession(RedisSession redisSession) {

        String redisSessionId = redisSession.getSessionId();

        try {
            String stringRedisSession = objectMapper.writeValueAsString(redisSession);
            jedis.set(redisSessionId, stringRedisSession);
            jedis.expire(redisSessionId, 4000);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RedisSession loadSession(String sessionId) {

        String stringRedisSession = jedis.get(sessionId);
        if (stringRedisSession == null)
            return null;

        try {
            return objectMapper.readValue(stringRedisSession, RedisSession.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void deleteSession(String sessionId) {
        jedis.del(sessionId);
    }
}
