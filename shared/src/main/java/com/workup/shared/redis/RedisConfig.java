package com.workup.shared.redis;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisStandaloneConfiguration;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.Jackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;
import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig {

  @Value("${spring.cache.host}")
  private String REDIS_HOST;

  @Value("${spring.cache.port}")
  private Integer REDIS_PORT;

  @Bean
  public JedisConnectionFactory jedisConnectionFactory() {
    RedisStandaloneConfiguration config = new RedisStandaloneConfiguration(REDIS_HOST, REDIS_PORT);
    JedisConnectionFactory factory = new JedisConnectionFactory(config);
    factory.setPoolConfig(jedisPoolConfig());
    return factory;
  }

  @Bean
  public JedisPoolConfig jedisPoolConfig() {
    JedisPoolConfig poolConfig = new JedisPoolConfig();
    poolConfig.setMaxTotal(50);
    poolConfig.setMaxIdle(20);
    poolConfig.setMinIdle(5);
    return poolConfig;
  }

  @Bean
  public RedisTemplate<String, Object> redisTemplate() {
    RedisTemplate<String, Object> template = new RedisTemplate<>();
    template.setConnectionFactory(jedisConnectionFactory());

    template.setKeySerializer(new StringRedisSerializer());

    Jackson2JsonRedisSerializer<Object> serializer =
        new Jackson2JsonRedisSerializer<>(Object.class);
    template.setValueSerializer(serializer);
    template.setHashValueSerializer(serializer);

    return template;
  }
}
