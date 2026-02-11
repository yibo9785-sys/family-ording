package com.example.family_ording.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

import java.time.Duration;

@Component
public class RedisUtil {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    public void set(String key, Object value) {
        redisTemplate.opsForValue().set(key, value);
    }

    public void setWithDuration(String key, Object value, Duration duration) {
        redisTemplate.opsForValue().set(key, value, duration);
    }

    public void setWithSeconds(String key, Object value, long seconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofSeconds(seconds));
    }

    public void setWithMinutes(String key, Object value, long minutes) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMinutes(minutes));
    }

    public void setWithHours(String key, Object value, long hours) {
        redisTemplate.opsForValue().set(key, value, Duration.ofHours(hours));
    }

    public void setWithDays(String key, Object value, long days) {
        redisTemplate.opsForValue().set(key, value, Duration.ofDays(days));
    }

    public void setWithMilliseconds(String key, Object value, long milliseconds) {
        redisTemplate.opsForValue().set(key, value, Duration.ofMillis(milliseconds));
    }

    public Object get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }
// 判断key是否存在
    public Boolean exists(String key) {
        return redisTemplate.hasKey(key);
    }
// 自增
    public Long increment(String key) {
        return redisTemplate.opsForValue().increment(key);// 返回自增后的值
    }

    public Long increment(String key, long delta) {
        return redisTemplate.opsForValue().increment(key, delta);
    }

}
