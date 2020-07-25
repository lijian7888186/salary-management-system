package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.domain.DeleteGroups;
import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigParam;
import com.cloud.search.salarymanagementsystem.service.CustomSalaryConfigService;
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
 * @date 2020/7/23 18:23
 * @desc
 */
@RestController
@Api(tags = "自定义工资配置接口")
@Permission
@RequestMapping("customSalaryConfig")
@Slf4j
public class CustomSalaryConfigController {
    @Resource
    private CustomSalaryConfigService customSalaryConfigService;
    @PostMapping("findList")
    @ApiOperation(value = "查找列表")
    public ResponseView findList(@RequestBody @Validated SalaryConfigPageParam salaryConfigPageParam) {
        ResponseView responseView = customSalaryConfigService.findByPage(salaryConfigPageParam);
        return responseView;
    }

    @PostMapping("addConfig")
    @ApiOperation(value = "添加配置")
    public ResponseView addConfig(@RequestBody @Validated(InsertGroups.class) SalaryConfigParam salaryConfigParam) {
        customSalaryConfigService.addConfig(salaryConfigParam);
        return ResponseView.buildSuccess();
    }

    @PostMapping("deleteConfig")
    @ApiOperation(value = "添加配置")
    public ResponseView deleteConfig(@RequestBody @Validated(DeleteGroups.class)SalaryConfigParam salaryConfigParam) {
        customSalaryConfigService.deleteConfig(salaryConfigParam);
        return ResponseView.buildSuccess();
    }
}
