package com.cloud.search.salarymanagementsystem.utils;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.Objects;

/**
 * @author lijian
 * @date 2020/7/20 17:10
 * @desc
 */
@Component
public class CacheUtil implements ApplicationContextAware {
    private static CacheManager cacheManager;

    public static Cache<String, String> createStringCache(String name) {
        return createCache(name, String.class, String.class, 30);
    }

    public static Cache createCache(String name, Class k, Class v, int time) {
        Cache cache = cacheManager.createCache(name,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(k, v, ResourcePoolsBuilder.heap(10))
                        .withExpiry(ExpiryPolicyBuilder.timeToIdleExpiration(Duration.ofMinutes(time))));
        return cache;
    }

    public static Cache createCacheNoExpire(String name) {
        Cache cache = cacheManager.createCache(name,
                CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)));
        return cache;
    }

    public static Cache getCache(String name, Class k, Class v) {
        return cacheManager.getCache(name, k, v);
    }

    public static Cache<String, String> getStringCache(String name) {
        return cacheManager.getCache(name, String.class, String.class);
    }

    public static void deleteCache(String name) {
        if (Objects.nonNull(getStringCache(name))) {
            cacheManager.removeCache(name);
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        cacheManager = applicationContext.getBean(CacheManager.class);
    }
}
