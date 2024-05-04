package com.workup.shared.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class RedisService {

  private final RedisTemplate<String, Object> redisTemplate;
  private final ObjectMapper objectMapper;

  private String buildKey(String prefix, String key) {
    return prefix + "::" + key;
  }

  public void setValue(String key, Object value) {
    try {
      String cacheName = value.getClass().getSimpleName();
      String cacheKey = buildKey(cacheName, key);
      redisTemplate.opsForValue().set(cacheKey, objectMapper.writeValueAsString(value));
    } catch (JsonProcessingException e) {
      e.printStackTrace(); // TODO: use logger
    }
  }

  public Object getValue(String key, Class<?> classDefinition) {
    try {
      String cacheName = classDefinition.getSimpleName();
      String cacheKey = buildKey(cacheName, key);
      String value = (String) redisTemplate.opsForValue().get(cacheKey);
      if (value == null) {
        return null;
      }
      return objectMapper.readValue(value, classDefinition);
    } catch (JsonProcessingException e) {
      e.printStackTrace(); // TODO: use logger
      return null;
    }
  }

  public void deleteValue(String key) {
    redisTemplate.delete(key);
  }

  public void clearCache() {
    redisTemplate.execute(
        (RedisCallback<Object>)
            connection -> {
              connection.execute("flushAll");
              return null;
            });
  }
}
