package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.Role;
import com.cloud.search.salarymanagementsystem.domain.UserRole;
import com.cloud.search.salarymanagementsystem.domain.views.RolePageParam;
import com.cloud.search.salarymanagementsystem.mapper.RoleMapper;
import com.cloud.search.salarymanagementsystem.mapper.UserRoleMapper;
import com.cloud.search.salarymanagementsystem.service.RoleService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lijian
 * @date 2020/7/21 17:16
 * @desc
 */
@Service
@Slf4j
public class RoleServiceImpl implements RoleService {
    @Resource
    private RoleMapper roleMapper;
    @Resource
    private UserRoleMapper userRoleMapper;
    @Override
    public ResponseView findList(RolePageParam rolePageParam) {
        Page<Role> page = PageHelper.startPage(rolePageParam.getPage(), rolePageParam.getPageSize());
        Role role = new Role();
        role.setYn(1);
        roleMapper.select(role);
        ResponseView responseView = ResponseView.buildSuccess(page.getResult());
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Override
    public void addRole(Role role) {
        roleMapper.insertSelective(role);
    }

    @Override
    public void deleteRole(Role role) {
        UserRole userRole = new UserRole();
        userRole.setYn(1);
        userRole.setRoleId(role.getId());
        int count = userRoleMapper.selectCount(userRole);
        if (count > 0) {
            throw new RuntimeException("角色有用户,不能删除");
        }
        roleMapper.updateByPrimaryKeySelective(role);
    }

    @Override
    public List<Role> findAll(Role role) {
        return roleMapper.select(role);
    }
}
