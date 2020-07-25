package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.config.UserInfoContext;
import com.cloud.search.salarymanagementsystem.domain.*;
import com.cloud.search.salarymanagementsystem.domain.views.OptionView;
import com.cloud.search.salarymanagementsystem.domain.views.RolePageParam;
import com.cloud.search.salarymanagementsystem.domain.views.RoleParam;
import com.cloud.search.salarymanagementsystem.service.RoleService;
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
import java.util.stream.Collectors;

/**
 * @author lijian
 * @date 2020/7/21 17:09
 * @desc
 */
@RestController
@Api(tags = "角色接口")
@Permission
@RequestMapping("role")
@Slf4j
public class RoleController {
    @Resource
    private RoleService roleService;
    @PostMapping("findList")
    @ApiOperation(value = "查找列表")
    public ResponseView findList(@RequestBody @Validated RolePageParam rolePageParam) {
        ResponseView responseView = roleService.findList(rolePageParam);
        return responseView;
    }

    @PostMapping("addRole")
    @ApiOperation(value = "添加角色")
    public ResponseView addRole(@RequestBody @Validated(InsertGroups.class) RoleParam roleParam) {
        Role role = new Role();
        role.setRoleName(roleParam.getRoleName());
        roleService.addRole(role);
        return ResponseView.buildSuccess();
    }

    @PostMapping("deleteRole")
    @ApiOperation(value = "删除角色")
    public ResponseView deleteRole(@RequestBody @Validated(UpdateGroups.class) RoleParam roleParam) {
        Role role = new Role();
        role.setId(roleParam.getId());
        role.setYn(0);
        roleService.deleteRole(role);
        return ResponseView.buildSuccess();
    }

    @PostMapping("findRoleOption")
    @ApiOperation(value = "查找角色名称的下拉框")
    public ResponseView findDeptOption() {
        Role role = new Role();
        role.setYn(1);
        List<Role> list = roleService.findAll(role);
        List<OptionView> collect = list.stream().map(view -> {
            OptionView optionView = new OptionView();
            optionView.setLabel(view.getRoleName());
            optionView.setValue(view.getId());
            return optionView;
        }).collect(Collectors.toList());
        ResponseView responseView = ResponseView.buildSuccess(collect);
        return responseView;
    }
}
