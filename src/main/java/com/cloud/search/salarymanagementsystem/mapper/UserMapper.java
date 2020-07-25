package com.cloud.search.salarymanagementsystem.mapper;

import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.views.UserListParam;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author lijian
 * @date 2020/3/17 21:08
 * @desc
 */
public interface UserMapper extends BaseMapper<User> {
    List<User> findList(UserListParam userListParam);
}
