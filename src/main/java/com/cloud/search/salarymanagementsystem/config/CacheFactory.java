package com.cloud.search.salarymanagementsystem.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.time.Duration;

/**
 * @author lijian
 * @date 2020/6/30 19:25
 * @desc
 */
public class CacheFactory {

    public CacheManager cacheManager;

    public void init() {
        System.out.println("init");
        cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                .withCache("loginCache",
                        CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class,
                                ResourcePoolsBuilder.heap(10))
                                .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(30))))
                .build();
        cacheManager.init();
    }

    public void close() {
        cacheManager.close();
        System.out.println("PreDestroy");
    }

}
