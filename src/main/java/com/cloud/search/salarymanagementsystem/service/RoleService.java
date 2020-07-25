package com.cloud.search.salarymanagementsystem.service;

import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.Role;
import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.views.RolePageParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserParam;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/20 19:20
 * @desc
 */
public interface RoleService {
    ResponseView findList(RolePageParam rolePageParam);

    void addRole(Role role);

    void deleteRole(Role role);

    List<Role> findAll(Role role);

}
