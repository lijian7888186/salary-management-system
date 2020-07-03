package com.cloud.search.salarymanagementsystem.config;

import com.alibaba.druid.pool.DruidDataSource;
import org.ehcache.Cache;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

/**
 * @author lijian
 * @date 2020/3/17 20:15
 * @desc
 */
@Configuration
@PropertySource("datasource.properties")
public class MysqlConfiguration {

    @ConfigurationProperties(prefix = "mysql.salary")
    @Bean(name = "salary")
    public Map<String, String> databaseProperties() {
        return new HashMap<>();
    }


    @Bean
    public DataSource getDataSource(Map<String, String> salary) {
        DruidDataSource druidDataSource = new DruidDataSource();
        druidDataSource.setDriverClassName(salary.get("driver.className"));
        druidDataSource.setUrl(salary.get("url"));
        druidDataSource.setUsername(salary.get("userName"));
        druidDataSource.setPassword(salary.get("password"));
        return druidDataSource;
    }

    @Bean
    public PlatformTransactionManager getManager(DataSource dataSource, Cache cache) {
        System.out.println(cache);
        return new DataSourceTransactionManager(dataSource);
    }
}
