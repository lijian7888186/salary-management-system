package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import com.cloud.search.salarymanagementsystem.domain.UpdateGroups;
import lombok.Data;

import javax.validation.constraints.NotNull;

/**
 * @author lijian
 * @date 2020/7/23 10:25
 * @desc
 */
@Data
public class DeptParam {
    @NotNull(groups = UpdateGroups.class)
    private Long id;
    @NotNull(groups = InsertGroups.class)
    private String deptName;
}
