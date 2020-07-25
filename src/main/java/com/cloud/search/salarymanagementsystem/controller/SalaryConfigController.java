package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.domain.DeleteGroups;
import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigParam;
import com.cloud.search.salarymanagementsystem.service.SalaryConfigService;
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
@Api(tags = "工资配置接口")
@Permission
@RequestMapping("salaryConfig")
@Slf4j
public class SalaryConfigController {
    @Resource
    private SalaryConfigService salaryConfigService;
    @PostMapping("findList")
    @ApiOperation(value = "查找列表")
    public ResponseView findList(@RequestBody @Validated SalaryConfigPageParam salaryConfigPageParam) {
        ResponseView responseView = salaryConfigService.findByPage(salaryConfigPageParam);
        return responseView;
    }

    @PostMapping("addConfig")
    @ApiOperation(value = "添加配置")
    public ResponseView addConfig(@RequestBody @Validated(InsertGroups.class)SalaryConfigParam salaryConfigParam) {
        salaryConfigService.addConfig(salaryConfigParam);
        return ResponseView.buildSuccess();
    }

    @PostMapping("deleteConfig")
    @ApiOperation(value = "添加配置")
    public ResponseView deleteConfig(@RequestBody @Validated(DeleteGroups.class)SalaryConfigParam salaryConfigParam) {
        salaryConfigService.deleteConfig(salaryConfigParam);
        return ResponseView.buildSuccess();
    }
}
