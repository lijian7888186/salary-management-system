package com.cloud.search.salarymanagementsystem.config;

/**
 * @author lijian
 * @date 2020/7/21 14:14
 * @desc
 */
public class UserInfoContext {
    public static final ThreadLocal<UserInfo> threadLocal = new ThreadLocal<>();
    public static void setUserInfo(UserInfo userInfo) {
        threadLocal.set(userInfo);
    }
    public static UserInfo getUserInfo() {
        UserInfo userInfo = threadLocal.get();
        if (userInfo == null) {
            throw new RuntimeException("用户信息错误");
        }
        return userInfo;
    }
    public static void remove() {
        threadLocal.remove();
    }
}
