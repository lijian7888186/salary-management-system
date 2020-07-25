package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.config.PermissionConfiguration;
import com.cloud.search.salarymanagementsystem.domain.DeleteGroups;
import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.*;
import com.cloud.search.salarymanagementsystem.service.RolePermissionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lijian
 * @date 2020/7/23 15:45
 * @desc
 */
@RestController
@RequestMapping("rolePermission")
@Api(tags = "角色权限接口")
@Permission
@Slf4j
public class RolePermissionController {
    @Resource
    private RolePermissionService rolePermissionService;
    @PostMapping("findList")
    @ApiOperation(value = "查询角色权限列表")
    public ResponseView findList(@RequestBody @Validated RolePermissionPageParam rolePermissionPageParam) {
        ResponseView responseView = rolePermissionService.findList(rolePermissionPageParam);
        return responseView;
    }

    @PostMapping("findRolePermissions")
    @ApiOperation(value = "查找角色权限列表")
    public ResponseView findRolePermissions(@RequestBody @Validated RolePermissionParam rolePermissionParam) {
        List<RolePermissionView> rolePermissions = rolePermissionService.findRolePermissions(rolePermissionParam);
        ResponseView responseView = ResponseView.buildSuccess(rolePermissions);
        return responseView;
    }

    @PostMapping("addRolePermission")
    @ApiOperation(value = "添加角色权限")
    public ResponseView addRolePermission(@RequestBody @Validated(InsertGroups.class) RolePermissionParam rolePermissionParam) {
        rolePermissionService.addRolePermission(rolePermissionParam);
        return ResponseView.buildSuccess();
    }

    @PostMapping("deleteRolePermission")
    @ApiOperation(value = "删除角色权限")
    public ResponseView deleteRolePermission(@RequestBody @Validated(DeleteGroups.class) RolePermissionParam rolePermissionParam) {
        rolePermissionService.deleteRolePermission(rolePermissionParam);
        return ResponseView.buildSuccess();
    }

}
