package com.project.weather_api.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.cache.concurrent.ConcurrentMapCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

@Configuration
@EnableCaching
public class AppConfig {
    @Bean
    public CacheManager cacheManager() {
        CaffeineCacheManager manager= new CaffeineCacheManager("weather");
        manager.setCaffeine(Caffeine.newBuilder()
                .maximumSize(4) //max entries 4
                .expireAfterWrite(10, TimeUnit.MINUTES) // delete entries after 10 minutes
        );
        return manager;
        //return new ConcurrentMapCacheManager("weather");
    }
}
