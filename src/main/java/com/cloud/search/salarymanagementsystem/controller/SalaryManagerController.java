package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryManagerPageParam;
import com.cloud.search.salarymanagementsystem.service.SalaryManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @author lijian
 * @date 2020/7/23 17:25
 * @desc
 */
@RestController
@Api(tags = "工资管理接口")
@Permission
@RequestMapping("salaryManager")
@Slf4j
public class SalaryManagerController {
    @Resource
    private SalaryManagerService salaryManagerService;
    @PostMapping("findList")
    @ApiOperation(value = "查询工资列表")
    public ResponseView findList(@RequestBody @Validated SalaryManagerPageParam salaryManagerPageParam) {
        ResponseView responseView = salaryManagerService.findByPage(salaryManagerPageParam);
        return responseView;
    }
}
