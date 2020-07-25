package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.domain.DeleteGroups;
import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.UserRolePageParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserRoleParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserRoleView;
import com.cloud.search.salarymanagementsystem.service.UserRoleService;
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
 * @date 2020/7/23 13:52
 * @desc
 */
@RestController
@Api(tags = "用户角色接口")
@Permission
@RequestMapping("userRole")
@Slf4j
public class UserRoleController {
    @Resource
    private UserRoleService userRoleService;
    @PostMapping("findList")
    @ApiOperation(value = "查找列表")
    public ResponseView findList(@RequestBody @Validated UserRolePageParam userRolePageParam) {
        ResponseView responseView = userRoleService.findList(userRolePageParam);
        return responseView;
    }

    @PostMapping("findUserRoles")
    @ApiOperation(value = "查找用户角色列表")
    public ResponseView findUserRoles(@RequestBody @Validated UserRoleParam userRoleParam) {
        List<UserRoleView> userRoles = userRoleService.findUserRoles(userRoleParam);
        ResponseView responseView = ResponseView.buildSuccess(userRoles);
        return responseView;
    }

    @PostMapping("addUserRole")
    @ApiOperation(value = "添加用户角色")
    public ResponseView addUserRole(@RequestBody @Validated(InsertGroups.class) UserRoleParam userRoleParam) {
        userRoleService.addUserRole(userRoleParam);
        return ResponseView.buildSuccess();
    }

    @PostMapping("deleteUserRole")
    @ApiOperation(value = "删除用户角色")
    public ResponseView deleteUserRole(@RequestBody @Validated(DeleteGroups.class) UserRoleParam userRoleParam) {
        userRoleService.deleteUserRole(userRoleParam);
        return ResponseView.buildSuccess();
    }

}
