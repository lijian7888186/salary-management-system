package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.DeleteGroups;
import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijian
 * @date 2020/7/23 14:15
 * @desc
 */
@Data
public class RolePermissionParam {
    @NotNull
    private Long roleId;
    @NotNull(groups = InsertGroups.class)
    private String permissionUrl;
    @NotNull(groups = DeleteGroups.class)
    private Long id;
}
