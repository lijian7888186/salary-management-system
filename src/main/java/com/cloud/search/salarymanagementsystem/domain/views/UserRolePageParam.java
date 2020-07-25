package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijian
 * @date 2020/7/21 17:12
 * @desc
 */
@Data
public class UserRolePageParam {
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;
}
