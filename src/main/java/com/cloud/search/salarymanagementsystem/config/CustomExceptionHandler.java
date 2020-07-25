package com.cloud.search.salarymanagementsystem.config;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @author lijian
 * @date 2020/3/17 21:16
 * @desc
 */
@ControllerAdvice
@Slf4j
public class CustomExceptionHandler {
    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Object handlerException(Exception e) {
        log.error("ExceptionHandler error", e);
        ResponseView responseView = ResponseView.buildError(e.getMessage());
        return responseView;
    }
}
