package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.CustomSalaryConfig;
import lombok.Data;

/**
 * @author lijian
 * @date 2020/7/23 18:44
 * @desc
 */
@Data
public class CustomSalaryConfigView extends CustomSalaryConfig {
    private String userName;
    private String deptName;
    private String typeStr;
}
