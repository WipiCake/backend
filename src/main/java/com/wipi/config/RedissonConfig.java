package com.wipi.config;

import lombok.RequiredArgsConstructor;
import org.redisson.Redisson;
import org.redisson.api.RedissonClient;
import org.redisson.config.Config;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@RequiredArgsConstructor
public class RedissonConfig {

    private final RedisProperties redisProperties;

    @Bean
    public RedissonClient redissonClient() {
        Config config = new Config();
        String REDISSON_HOST_PREFIX = "redis://";
        config.useSingleServer().setAddress(REDISSON_HOST_PREFIX + redisProperties.getHost()+":"+redisProperties.getPort());
        return Redisson.create(config);
    }

}