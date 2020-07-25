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
public class UserRoleParam {
    @NotNull
    private Long userId;
    @NotNull(groups = InsertGroups.class)
    private Long roleId;
    @NotNull(groups = DeleteGroups.class)
    private Long id;
}
