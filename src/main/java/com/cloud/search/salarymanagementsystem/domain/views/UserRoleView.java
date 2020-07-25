package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

/**
 * @author lijian
 * @date 2020/7/23 14:16
 * @desc
 */
@Data
public class UserRoleView {
    private Long userId;
    private String userName;
    private Long roleId;
    private Long id;
    private String roleName;
}
