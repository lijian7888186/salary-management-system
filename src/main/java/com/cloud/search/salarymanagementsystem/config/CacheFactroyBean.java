package com.cloud.search.salarymanagementsystem.config;

import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.stereotype.Component;

/**
 * @author lijian
 * @date 2020/6/30 19:32
 * @desc
 */
@Component
public class CacheFactroyBean implements FactoryBean<CacheManager>, DisposableBean {
    private CacheFactory factory;
    @Override
    public CacheManager getObject() throws Exception {
        factory = new CacheFactory();
        factory.init();
        return factory.cacheManager;
    }

    @Override
    public Class<?> getObjectType() {
        return CacheManager.class;
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
