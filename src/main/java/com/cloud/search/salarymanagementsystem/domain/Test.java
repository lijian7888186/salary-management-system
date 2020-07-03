package com.cloud.search.salarymanagementsystem.domain;

import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijian
 * @date 2020/3/17 21:08
 * @desc
 */
@Data
public class Test {
    private Integer id;
    @NotNull(message = "not null", groups = TestGroups.class)
    private String name;
    private String type;
}
