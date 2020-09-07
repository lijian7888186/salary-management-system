package com.cloud.search.salarymanagementsystem.service;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.RolePermissionPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.RolePermissionParam;
import com.cloud.search.salarymanagementsystem.domain.views.RolePermissionView;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/23 15:47
 * @desc
 */
public interface RolePermissionService {
    ResponseView findList(RolePermissionPageParam rolePermissionPageParam);

    List<RolePermissionView> findRolePermissions(RolePermissionParam rolePermissionParam);

    void addRolePermission(RolePermissionParam rolePermissionParam);

    void deleteRolePermission(RolePermissionParam rolePermissionParam);

    boolean checkPermission(String url);

    void addRoleAllPermission();

}
