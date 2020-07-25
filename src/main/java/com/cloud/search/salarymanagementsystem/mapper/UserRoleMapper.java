package com.cloud.search.salarymanagementsystem.mapper;

import com.cloud.search.salarymanagementsystem.domain.Role;
import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.UserRole;
import com.cloud.search.salarymanagementsystem.domain.views.UserRoleView;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author lijian
 * @date 2020/3/17 21:08
 * @desc
 */
public interface UserRoleMapper extends BaseMapper<UserRole> {
    List<UserRoleView> findByGroup();
}
