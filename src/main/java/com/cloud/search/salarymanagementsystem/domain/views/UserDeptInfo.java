package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

/**
 * @author lijian
 * @date 2020/7/22 16:28
 * @desc
 */
@Data
public class UserDeptInfo {
    private Long id;
    private Long deptId;
    private String phone;
    private Integer level;
}
