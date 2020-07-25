package com.cloud.search.salarymanagementsystem.mapper;

import com.cloud.search.salarymanagementsystem.domain.Dept;
import com.cloud.search.salarymanagementsystem.domain.RolePermission;
import com.cloud.search.salarymanagementsystem.domain.views.RolePermissionView;
import com.cloud.search.salarymanagementsystem.domain.views.UserRolePermissionParam;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/23 15:47
 * @desc
 */
public interface RolePermissionMapper extends BaseMapper<RolePermission> {
    List<RolePermissionView> findByGroup();

    Integer findUserPermissionByUrlCount(UserRolePermissionParam userRolePermissionParam);
}
