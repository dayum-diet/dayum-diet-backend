package com.dayum.dayum_diet.domain.token;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class TokenService {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    // AccessToken 저장
    public void storeAccessToken(String key, String token) {
        redisTemplate.opsForValue().set(key, token, 30, TimeUnit.MINUTES); // 30분 만료
    }

    // RefreshToken 저장
    public void storeRefreshToken(String key, String token) {
        redisTemplate.opsForValue().set(key, token, 7, TimeUnit.DAYS); // 7일 만료
    }

    // AccessToken 조회
    public String getAccessToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // RefreshToken 조회
    public String getRefreshToken(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    // Token 삭제
    public void deleteToken(String key) {
        redisTemplate.delete(key);
    }
}
