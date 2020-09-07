package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.PasswordGroups;
import com.cloud.search.salarymanagementsystem.domain.UpdateGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijian
 * @date 2020/7/21 15:17
 * @desc
 */
@Data
public class UpdateUserParam {
    @NotNull
    private Long id;
    @NotNull(groups = UpdateGroups.class)
    private String phone;
    @NotNull(groups = UpdateGroups.class)
    private Long deptId;
    @NotNull(groups = UpdateGroups.class)
    private Integer level;
    @NotNull(groups = PasswordGroups.class)
    private String password;
    @NotNull(groups = UpdateGroups.class)
    private String nickname;
}
