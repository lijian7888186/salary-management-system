package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijian
 * @date 2020/7/20 19:38
 * @desc
 */
@Data
public class UserParam {
    @NotNull
    private Integer page;
    @NotNull
    private Integer pageSize;
}
