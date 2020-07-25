package com.cloud.search.salarymanagementsystem.service;

import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.views.AddUserParam;
import com.cloud.search.salarymanagementsystem.domain.views.UpdateUserParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserDeptInfo;
import com.cloud.search.salarymanagementsystem.domain.views.UserParam;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/20 19:20
 * @desc
 */
public interface UserService {
    List<User> findList(User user);

    ResponseView<User> findPage(UserParam userParam);

    void assignmentUser(UserInfo userInfo);

    void addUser(AddUserParam addUserParam);

    void deleteUser(UpdateUserParam updateUserParam);

    void updateUser(UpdateUserParam updateUserParam);

    void updatePassword(UpdateUserParam updateUserParam);

    UserDeptInfo findUserById(UpdateUserParam updateUserParam);

}
