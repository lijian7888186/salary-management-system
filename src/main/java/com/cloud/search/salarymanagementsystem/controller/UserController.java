package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.config.UserInfoContext;
import com.cloud.search.salarymanagementsystem.domain.*;
import com.cloud.search.salarymanagementsystem.domain.views.*;
import com.cloud.search.salarymanagementsystem.service.UserService;
import com.cloud.search.salarymanagementsystem.utils.CacheUtil;
import com.cloud.search.salarymanagementsystem.utils.Consts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
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
 * @date 2020/7/20 19:35
 * @desc
 */
@RestController
@Api(tags = "用户接口")
@Permission
@RequestMapping("user")
@Slf4j
public class UserController {
    @Resource
    private UserService userService;
    @PostMapping("findList")
    @ApiOperation(value = "查找列表")
    public ResponseView findList(@RequestBody UserParam userParam) {
        ResponseView<User> responseView = userService.findPage(userParam);
        return responseView;
    }

    @PostMapping("addUser")
    @ApiOperation(value = "添加用户")
    public ResponseView addUser(@RequestBody @Validated AddUserParam addUserParam) {
        userService.addUser(addUserParam);
        return ResponseView.buildSuccess();
    }

    @PostMapping("deleteUser")
    @ApiOperation(value = "删除用户")
    public ResponseView deleteUser(@RequestBody @Validated UpdateUserParam updateUserParam) {
        UserInfo userInfo = UserInfoContext.getUserInfo();
        if (userInfo.getUserId().equals(updateUserParam.getId())) {
            return ResponseView.buildError("不能删除自己的账号信息");
        }
        userService.deleteUser(updateUserParam);
        return ResponseView.buildSuccess();
    }

    @PostMapping("updateUser")
    @ApiOperation(value = "修改用户")
    public ResponseView updateUser(@RequestBody @Validated(UpdateGroups.class) UpdateUserParam updateUserParam) {
        userService.updateUser(updateUserParam);
        return ResponseView.buildSuccess();
    }

    @PostMapping("updatePassword")
    @ApiOperation(value = "修改用户密码")
    public ResponseView updatePassword(@RequestBody @Validated(PasswordGroups.class) UpdateUserParam updateUserParam) {
        userService.updatePassword(updateUserParam);
        Cache<String, String> stringCache = CacheUtil.getStringCache(Consts.UPDATE_PASSWORD);
        if (stringCache == null) {
            stringCache = CacheUtil.createCacheNoExpire(Consts.UPDATE_PASSWORD);
        }
        stringCache.put(updateUserParam.getId().toString(), "");
        return ResponseView.buildSuccess();
    }

    @PostMapping("findUserById")
    @ApiOperation(value = "根据id查询用户")
    public ResponseView findUserById(@RequestBody @Validated UpdateUserParam updateUserParam) {
        UserDeptInfo info = userService.findUserById(updateUserParam);
        ResponseView responseView = ResponseView.buildSuccess(info);
        return responseView;
    }

    @PostMapping("findUserOption")
    @ApiOperation(value = "查找用户名称的下拉框")
    public ResponseView findDeptOption() {
        User user = new User();
        user.setYn(1);
        List<User> list = userService.findList(user);
        List<OptionView> collect = list.stream().map(view -> {
            OptionView optionView = new OptionView();
            optionView.setLabel(view.getUserName());
            optionView.setValue(view.getId());
            return optionView;
        }).collect(Collectors.toList());
        ResponseView responseView = ResponseView.buildSuccess(collect);
        return responseView;
    }

}

