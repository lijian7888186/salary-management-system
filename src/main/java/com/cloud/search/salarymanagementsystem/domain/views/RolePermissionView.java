package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

/**
 * @author lijian
 * @date 2020/7/23 14:16
 * @desc
 */
@Data
public class RolePermissionView {
    private Long roleId;
    private String roleName;
    private Long id;
    private String permissionUrl;
    private String permissionName;
}
