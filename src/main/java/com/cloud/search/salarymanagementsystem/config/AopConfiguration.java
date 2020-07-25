package com.cloud.search.salarymanagementsystem.config;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

/**
 * @author lijian
 * @date 2020/4/22 14:29
 * @desc
 */
@Aspect
@Component
public class AopConfiguration {
    @Pointcut("execution(* com.cloud.search.salarymanagementsystem.service.*.*(..))")
    public void pointcut(){

    }

    @Before("pointcut()")
    public void test1() {
        System.out.println("test----------------");
    }
}
