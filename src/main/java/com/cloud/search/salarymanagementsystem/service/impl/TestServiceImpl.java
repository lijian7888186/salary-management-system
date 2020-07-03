package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.domain.Test;
import com.cloud.search.salarymanagementsystem.mapper.TestMapper;
import com.cloud.search.salarymanagementsystem.service.TestService;
import com.github.pagehelper.PageHelper;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.ehcache.config.Configuration;
import org.ehcache.config.builders.CacheConfigurationBuilder;
import org.ehcache.config.builders.CacheManagerBuilder;
import org.ehcache.config.builders.ExpiryPolicyBuilder;
import org.ehcache.config.builders.ResourcePoolsBuilder;
import org.ehcache.core.Ehcache;
import org.ehcache.xml.XmlConfiguration;
import org.springframework.cache.config.CacheManagementConfigUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.net.URL;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author lijian
 * @date 2020/3/17 21:10
 * @desc
 */
@Service
public class TestServiceImpl implements TestService {
    @Resource
    private TestMapper testMapper;
    @Override
    public List<Test> findAll() {
        PageHelper.startPage(1, 1);
        Test test = new Test();
        test.setId(1);
        List<Test> list = testMapper.select(test);
        Map<String, String> map = new HashMap<>();
        map.put("name", "test");
        testMapper.findTest(map);
        return list;
    }

    static class Test1 {
        public static void main(String[] args) {
            CacheManager cacheManager = CacheManagerBuilder.newCacheManagerBuilder()
                    .withCache("preConfigured",
                            CacheConfigurationBuilder.newCacheConfigurationBuilder(Long.class, String.class, ResourcePoolsBuilder.heap(10)))
                    .build();
            cacheManager.init();
            Cache<String, String> test = cacheManager.createCache("test", CacheConfigurationBuilder.newCacheConfigurationBuilder(String.class, String.class, ResourcePoolsBuilder.heap(10)).withExpiry(ExpiryPolicyBuilder.timeToLiveExpiration(Duration.ofSeconds(5))));
            test.put("test", "test");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            test.put("test1", "test1");
            String test1 = test.get("test");
            System.out.println(test1);
            test.put("test", "test");
            try {
                TimeUnit.SECONDS.sleep(3);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            String test2 = test.get("test");
            System.out.println(test2);
        }
    }
}
