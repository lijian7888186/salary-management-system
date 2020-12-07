package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.config.PermissionConfiguration;
import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.config.UserInfoContext;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.RolePermission;
import com.cloud.search.salarymanagementsystem.domain.UserRole;
import com.cloud.search.salarymanagementsystem.domain.views.*;
import com.cloud.search.salarymanagementsystem.mapper.RolePermissionMapper;
import com.cloud.search.salarymanagementsystem.mapper.UserRoleMapper;
import com.cloud.search.salarymanagementsystem.service.RolePermissionService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lijian
 * @date 2020/7/23 15:47
 * @desc
 */
@Service
public class RolePermissionServiceImpl implements RolePermissionService {
    @Resource
    private RolePermissionMapper rolePermissionMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Override
    public ResponseView findList(RolePermissionPageParam rolePermissionPageParam) {
        Page<RolePermissionView> page = PageHelper.startPage(rolePermissionPageParam.getPage(), rolePermissionPageParam.getPageSize(), "roleId desc");
        List<RolePermissionView> list = rolePermissionMapper.findByGroup();
        if (CollectionUtils.isEmpty(list)) {
            return ResponseView.buildSuccess();
        }
        ResponseView responseView = ResponseView.buildSuccess(list);
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Override
    public List<RolePermissionView> findRolePermissions(RolePermissionParam rolePermissionParam) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setYn(1);
        rolePermission.setRoleId(rolePermissionParam.getRoleId());
        List<RolePermission> list = rolePermissionMapper.select(rolePermission);
        List<RolePermissionView> views = new ArrayList<>();
        Map<String, PermissionUrlInfo> infoMap = PermissionConfiguration.INFO_MAP;
        list.forEach(view -> {
            RolePermissionView permissionView = new RolePermissionView();
            permissionView.setId(view.getId());
            permissionView.setRoleId(view.getRoleId());
            permissionView.setPermissionUrl(view.getPermissionUrl());
            permissionView.setPermissionName(infoMap.get(view.getPermissionUrl()).getName());
            views.add(permissionView);
        });
        return views;
    }

    @Override
    public void addRolePermission(RolePermissionParam rolePermissionParam) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setRoleId(rolePermissionParam.getRoleId());
        rolePermission.setPermissionUrl(rolePermissionParam.getPermissionUrl());
        rolePermission.setYn(1);
        int count = rolePermissionMapper.selectCount(rolePermission);
        if (count > 0) {
            throw new RuntimeException("角色已有权限,不能重复添加");
        }
        rolePermissionMapper.insertSelective(rolePermission);
    }

    @Override
    public void deleteRolePermission(RolePermissionParam rolePermissionParam) {
        RolePermission rolePermission = new RolePermission();
        rolePermission.setYn(0);
        rolePermission.setId(rolePermissionParam.getId());
        rolePermissionMapper.updateByPrimaryKeySelective(rolePermission);
    }

    @Override
    public boolean checkPermission(String url) {
        UserInfo userInfo = UserInfoContext.getUserInfo();
        UserRole userRole = new UserRole();
        userRole.setYn(1);
        userRole.setUserId(userInfo.getUserId());
        List<UserRole> userRoles = userRoleMapper.select(userRole);
        if (CollectionUtils.isEmpty(userRoles)) {
            return false;
        }
        List<Long> collect = userRoles.stream().map(UserRole::getRoleId).collect(Collectors.toList());
        UserRolePermissionParam param = new UserRolePermissionParam();
        if (url.startsWith("//")) {
            url = url.replace("//", "/");
        }
        param.setPermissionUrl(url);
        param.setRoleIds(collect);
        Integer count = rolePermissionMapper.findUserPermissionByUrlCount(param);
        if (count > 0) {
            return true;
        }
        return false;
    }

    @Override
    public void addRoleAllPermission() {
        List<PermissionUrlInfo> infos = PermissionConfiguration.INFOS;
        infos.forEach(view -> {
            RolePermission rolePermission = new RolePermission();
            rolePermission.setRoleId(1L);
            rolePermission.setPermissionUrl(view.getUrl());
            rolePermissionMapper.insertSelective(rolePermission);
        });
    }
}
