package com.cloud.search.salarymanagementsystem.service;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.UserRolePageParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserRoleParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserRoleView;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/23 13:54
 * @desc
 */
public interface UserRoleService {
    ResponseView findList(UserRolePageParam userRolePageParam);

    List<UserRoleView> findUserRoles(UserRoleParam userRoleParam);

    void addUserRole(UserRoleParam userRoleParam);

    void deleteUserRole(UserRoleParam userRoleParam);

}
