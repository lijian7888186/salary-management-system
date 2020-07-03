package com.cloud.search.salarymanagementsystem.config;

import org.ehcache.Cache;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author lijian
 * @date 2020/6/30 19:32
 * @desc
 */
@Component
public class CacheFactroyBean implements FactoryBean<Cache>, DisposableBean {
    private CacheFactory factory;
    @Override
    public Cache getObject() throws Exception {
        factory = new CacheFactory();
        factory.init();
        return factory.cache;
    }

    @Override
    public Class<?> getObjectType() {
        return Cache.class;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {
        if (factory != null) {
            factory.close();
        }
    }
}
