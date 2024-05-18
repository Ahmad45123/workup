package com.workup.users;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(com.workup.shared.redis.RedisConfig.class)
public class RedisConfig {}
