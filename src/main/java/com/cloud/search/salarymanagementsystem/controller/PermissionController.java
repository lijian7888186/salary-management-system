package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.config.PermissionConfiguration;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.OptionView;
import com.cloud.search.salarymanagementsystem.domain.views.PermissionPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.PermissionUrlInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijian
 * @date 2020/7/21 16:18
 * @desc
 */
@RestController
@RequestMapping("permission")
@Api(tags = "权限接口")
@Permission
@Slf4j
public class PermissionController {
    @PostMapping("findAllPermission")
    @ApiOperation(value = "查询所有权限列表")
    public ResponseView findAllPermission() {
        List<PermissionUrlInfo> infos = PermissionConfiguration.INFOS;
        ResponseView responseView = ResponseView.buildSuccess(infos);
        return responseView;
    }

    @PostMapping("findList")
    @ApiOperation(value = "查询所有权限列表")
    public ResponseView findList(@RequestBody @Validated PermissionPageParam permissionPageParam) {
        List<PermissionUrlInfo> infos = PermissionConfiguration.INFOS;
        int begin = (permissionPageParam.getPage() - 1) * permissionPageParam.getPageSize();
        int end = begin + permissionPageParam.getPageSize() - 1;
        ResponseView responseView = ResponseView.buildSuccess(infos.subList(begin, end));
        responseView.setTotal((long) infos.size());
        return responseView;
    }

    @PostMapping("findPermissionOption")
    @ApiOperation(value = "查找权限名称的下拉框")
    public ResponseView findPermissionOption() {
        List<PermissionUrlInfo> infos = PermissionConfiguration.INFOS;
        List<OptionView> collect = infos.stream().map(view -> {
            OptionView optionView = new OptionView();
            optionView.setLabel(view.getName());
            optionView.setValue(view.getUrl());
            return optionView;
        }).collect(Collectors.toList());
        ResponseView responseView = ResponseView.buildSuccess(collect);
        return responseView;
    }

}
