package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.DeleteGroups;
import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * @author lijian
 * @date 2020/7/23 14:15
 * @desc
 */
@Data
public class UserRolePermissionParam {
    private List<Long> roleIds;
    private String permissionUrl;
}
