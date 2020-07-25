package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.SalaryManager;
import lombok.Data;

/**
 * @author lijian
 * @date 2020/7/23 17:41
 * @desc
 */
@Data
public class SalaryManagerView extends SalaryManager {
    private String userName;
    private String deptName;
}
