package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.Role;
import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.UserRole;
import com.cloud.search.salarymanagementsystem.domain.views.UserRolePageParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserRoleParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserRoleView;
import com.cloud.search.salarymanagementsystem.mapper.RoleMapper;
import com.cloud.search.salarymanagementsystem.mapper.UserRoleMapper;
import com.cloud.search.salarymanagementsystem.service.UserRoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * @author lijian
 * @date 2020/7/23 13:55
 * @desc
 */
@Service
@Slf4j
public class UserRoleServiceImpl implements UserRoleService {
    @Resource
    private UserRoleMapper userRoleMapper;
    @Resource
    private RoleMapper roleMapper;
    @Override
    public ResponseView findList(UserRolePageParam userRolePageParam) {
        Page<UserRoleView> page = PageHelper.startPage(userRolePageParam.getPage(), userRolePageParam.getPageSize(), "userId desc");
        List<UserRoleView> list = userRoleMapper.findByGroup();
        if (CollectionUtils.isEmpty(list)) {
            return ResponseView.buildSuccess();
        }
        ResponseView responseView = ResponseView.buildSuccess(list);
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Override
    public List<UserRoleView> findUserRoles(UserRoleParam userRoleParam) {
        UserRole userRole = new UserRole();
        userRole.setUserId(userRoleParam.getUserId());
        userRole.setYn(1);
        List<UserRole> list = userRoleMapper.select(userRole);
        List<UserRoleView> views = new ArrayList<>();
        list.forEach(view -> {
            Role param = new Role();
            param.setId(view.getRoleId());
            param.setYn(1);
            Role role = roleMapper.selectOne(param);
            if (Objects.nonNull(role)) {
                UserRoleView roleView = new UserRoleView();
                roleView.setId(view.getId());
                roleView.setRoleId(view.getRoleId());
                roleView.setUserId(view.getUserId());
                roleView.setRoleName(role.getRoleName());
                views.add(roleView);
            }
        });
        return views;
    }

    @Override
    public void addUserRole(UserRoleParam userRoleParam) {
        UserRole userRole = new UserRole();
        userRole.setYn(1);
        userRole.setUserId(userRoleParam.getUserId());
        userRole.setRoleId(userRoleParam.getRoleId());
        int count = userRoleMapper.selectCount(userRole);
        if (count > 0) {
            throw new RuntimeException("用户已有角色,不能重复添加");
        }
        userRoleMapper.insertSelective(userRole);
    }

    @Override
    public void deleteUserRole(UserRoleParam userRoleParam) {
        UserRole userRole = new UserRole();
        userRole.setId(userRoleParam.getId());
        userRole.setYn(0);
        userRoleMapper.updateByPrimaryKeySelective(userRole);
    }
}
