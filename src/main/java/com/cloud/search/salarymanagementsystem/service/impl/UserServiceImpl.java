package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.config.UserInfoContext;
import com.cloud.search.salarymanagementsystem.domain.DeptUser;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.views.*;
import com.cloud.search.salarymanagementsystem.enums.UserLevelEnum;
import com.cloud.search.salarymanagementsystem.mapper.DeptUserMapper;
import com.cloud.search.salarymanagementsystem.mapper.UserMapper;
import com.cloud.search.salarymanagementsystem.service.UserService;
import com.cloud.search.salarymanagementsystem.utils.Consts;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;

import javax.annotation.Resource;
import javax.validation.constraints.NotNull;
import java.io.UnsupportedEncodingException;
import java.nio.charset.Charset;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijian
 * @date 2020/7/20 19:31
 * @desc
 */
@Service
@Slf4j
public class UserServiceImpl implements UserService {
    @Resource
    private UserMapper userMapper;
    @Resource
    private DeptUserMapper deptUserMapper;
    @Override
    public List<User> findList(User user) {
        List<User> list = userMapper.select(user);
        return list;
    }

    @Override
    public ResponseView<User> findPage(UserParam userParam) {
        UserInfo userInfo = UserInfoContext.getUserInfo();
        User user = new User();
        user.setYn(1);
        user.setId(userInfo.getUserId());
        List<User> users = userMapper.select(user);
        if (CollectionUtils.isEmpty(users)) {
            throw new RuntimeException("没有找到用户信息");
        }
        if (UserLevelEnum.NORMAL.getLevel().equals(userInfo.getUserLevel())) {
            ResponseView responseView = ResponseView.buildSuccess(users);
            responseView.setTotal(1L);
            return responseView;
        }
        DeptUser deptUser = new DeptUser();
        deptUser.setYn(1);
        deptUser.setDetpId(userInfo.getDeptId());
        List<DeptUser> allUser = deptUserMapper.select(deptUser);
        Page<User> page = PageHelper.startPage(userParam.getPage(), userParam.getPageSize(), "id desc");
        UserListParam userListParam = new UserListParam();
        userListParam.setUserIdList(allUser.stream().map(DeptUser::getUserId).collect(Collectors.toList()));
        userMapper.findList(userListParam);
        page.getResult().forEach(view -> view.setPassword(null));
        ResponseView responseView = ResponseView.buildSuccess(page.getResult());
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Override
    public void assignmentUser(UserInfo userInfo) {
        DeptUser param = new DeptUser();
        param.setYn(1);
        param.setUserId(userInfo.getUserId());
        List<DeptUser> deptUsers = deptUserMapper.select(param);
        DeptUser deptUser = deptUsers.get(0);
        userInfo.setUserLevel(deptUser.getUserLevel());
        userInfo.setDeptId(deptUser.getDetpId());
    }

    @Override
    @Transactional
    public void addUser(AddUserParam addUserParam) {
        User addUser = new User();
        String userName = addUserParam.getUserName();
        addUser.setYn(1);
        addUser.setUserName(userName);
        int count = userMapper.selectCount(addUser);
        if (count > 0) {
            throw new RuntimeException("用户名不能重复");
        }
        User user = new User();
        user.setUserName(addUserParam.getUserName());
        user.setPhone(addUserParam.getPhone());
        try {
            user.setPassword(DigestUtils.md5DigestAsHex(Consts.DEFAULT_PASSWORD.getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            log.error("addUser error", e);
            throw new RuntimeException("系统错误");
        }
        user.setNickname(addUserParam.getNickname());
        userMapper.insertSelective(user);
        DeptUser deptUser = new DeptUser();
        deptUser.setUserId(user.getId());
        deptUser.setDetpId(addUserParam.getDeptId());
        deptUser.setUserLevel(addUserParam.getLevel());
        deptUserMapper.insertSelective(deptUser);
    }

    @Override
    @Transactional
    public void deleteUser(UpdateUserParam updateUserParam) {
        DeptUser deptUser = new DeptUser();
        deptUser.setYn(1);
        deptUser.setUserId(updateUserParam.getId());
        List<DeptUser> list = deptUserMapper.select(deptUser);
        User user = new User();
        user.setYn(0);
        user.setId(updateUserParam.getId());
        userMapper.updateByPrimaryKeySelective(user);
        deptUser.setYn(0);
        deptUser.setId(list.get(0).getId());
        deptUserMapper.updateByPrimaryKeySelective(deptUser);
    }

    @Override
    @Transactional
    public void updateUser(UpdateUserParam updateUserParam) {
        DeptUser deptUser = new DeptUser();
        deptUser.setYn(1);
        deptUser.setUserId(updateUserParam.getId());
        List<DeptUser> list = deptUserMapper.select(deptUser);
        User user = new User();
        user.setId(updateUserParam.getId());
        user.setPhone(updateUserParam.getPhone());
        user.setNickname(updateUserParam.getNickname());
        userMapper.updateByPrimaryKeySelective(user);
        if (list.get(0).getUserLevel().equals(1)) {
            return;
        }
        if (list.get(0).getDetpId().equals(updateUserParam.getDeptId()) && list.get(0).getUserLevel().equals(updateUserParam.getLevel())) {
            return;
        }
        deptUser.setId(list.get(0).getId());
        deptUser.setDetpId(updateUserParam.getDeptId());
        deptUser.setUserLevel(updateUserParam.getLevel());
        deptUserMapper.updateByPrimaryKeySelective(deptUser);
    }

    @Override
    public void updatePassword(UpdateUserParam updateUserParam) {
        User user = new User();
        user.setId(updateUserParam.getId());
        try {
            user.setPassword(DigestUtils.md5DigestAsHex(updateUserParam.getPassword().getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            log.error("updatePassword error", e);
            throw new RuntimeException("系统错误");
        }
        userMapper.updateByPrimaryKeySelective(user);
    }

    @Override
    public UserDeptInfo findUserById(UpdateUserParam updateUserParam) {
        DeptUser deptUser = new DeptUser();
        deptUser.setYn(1);
        deptUser.setUserId(updateUserParam.getId());
        List<DeptUser> list = deptUserMapper.select(deptUser);
        User user = new User();
        user.setId(updateUserParam.getId());
        user.setYn(1);
        List<User> select = userMapper.select(user);
        UserDeptInfo info = new UserDeptInfo();
        info.setId(updateUserParam.getId());
        info.setDeptId(list.get(0).getDetpId());
        info.setLevel(list.get(0).getUserLevel());
        info.setPhone(select.get(0).getPhone());
        info.setNickname(select.get(0).getNickname());
        return info;
    }
}
