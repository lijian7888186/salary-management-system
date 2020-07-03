package com.cloud.search.salarymanagementsystem;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import tk.mybatis.spring.annotation.MapperScan;

@SpringBootApplication
@MapperScan(basePackages = "com.cloud.search.salarymanagementsystem.mapper")
@EnableTransactionManagement
public class SalaryManagementSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(SalaryManagementSystemApplication.class, args);
    }

}
