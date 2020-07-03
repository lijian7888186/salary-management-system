package com.cloud.search.salarymanagementsystem.config;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lijian
 * @date 2020/3/17 21:16
 * @desc
 */
@ControllerAdvice
public class TestExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e) {
        System.out.println("test exception");
        e.printStackTrace();
        return "error";
    }
}
