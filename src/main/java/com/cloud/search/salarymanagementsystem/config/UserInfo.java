package com.cloud.search.salarymanagementsystem.config;

import lombok.Data;

/**
 * @author lijian
 * @date 2020/7/21 14:18
 * @desc
 */
@Data
public class UserInfo {
    /**
     * 用户名
     */
    private String userName;
    /**
     * cookie中的用户信息
     */
    private String cookieName;

    /**
     * 用户id
     */
    private Long userId;

    /**
     * 用户职位
     */
    private Integer userLevel;

    /**
     * 部门id
     */
    private Long deptId;

}
