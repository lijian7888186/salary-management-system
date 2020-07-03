package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.domain.Test;
import com.cloud.search.salarymanagementsystem.domain.TestGroups;
import com.cloud.search.salarymanagementsystem.service.TestService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.List;
import java.util.Set;

/**
 * @author lijian
 * @date 2020/3/17 21:13
 * @desc
 */
@RestController
@RequestMapping("test")
@Slf4j
public class TestController {
    @Resource
    private TestService testService;
    @Resource
    private ThreadPoolTaskExecutor executor;
    @RequestMapping("findAll")
    public Object findAll(@Validated(TestGroups.class) Test test, BindingResult bindingResult) {
        List<ObjectError> allErrors = bindingResult.getAllErrors();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<Test>> validate = validator.validate(test, TestGroups.class);
        validate.forEach(k -> {
            System.out.println(k.getPropertyPath());
            System.out.println(k.getMessage());
            System.out.println(k.getRootBean());
            System.out.println(k.getRootBeanClass());
        });
        executor.execute(() -> {

        });
        allErrors.forEach(k -> System.out.println(k.getDefaultMessage()));
        List<Test> list = testService.findAll();
        System.out.println("测试");
        log.error("test");
        System.out.println(list);
        return list;
    }
}
