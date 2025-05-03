package com.project.universityservice.config;

import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cache.caffeine.CaffeineCacheManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import java.time.Duration;

/**
 * Configuration class for setting up caching with Caffeine.
 * Enables caching for the application and configures a cache manager
 * using Caffeine to store cache entries for a specified duration and size.
 */
@Configuration
@EnableCaching
public class CacheConfig {

    /**
     * Configures and returns a {@link CacheManager} for managing caches with Caffeine.
     * The cache entries will expire after 10 minutes and will store up to 100 records.
     *
     * @return a configured {@link CacheManager} using Caffeine.
     */
    @Bean
    @Primary
    public CacheManager cacheManager() {
        var caffeineCacheManager = new CaffeineCacheManager("feignCache");
        caffeineCacheManager.setCaffeine(
                Caffeine.newBuilder()
                        .expireAfterWrite(Duration.ofMinutes(10)) // Data expires after 10 minutes
                        .maximumSize(100) // Maximum 100 entries
        );
        return caffeineCacheManager;
    }
}
