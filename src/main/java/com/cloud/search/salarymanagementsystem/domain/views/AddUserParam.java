package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijian
 * @date 2020/7/21 15:17
 * @desc
 */
@Data
public class AddUserParam {
    @NotNull
    private String userName;
    @NotNull
    private String phone;
    @NotNull
    private Long deptId;
    @NotNull
    private Integer level;
    @NotNull
    private String nickname;
}
