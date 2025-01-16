package com.weather.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.cache.CacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RedisCacheConfig {
    
    @Autowired
    private CacheManager cacheManager;
    
    @Bean
    CommandLineRunner clearCache() {
        return args -> {
            cacheManager.getCacheNames()
                .forEach(cacheName -> cacheManager.getCache(cacheName).clear());
        };
    }
} 