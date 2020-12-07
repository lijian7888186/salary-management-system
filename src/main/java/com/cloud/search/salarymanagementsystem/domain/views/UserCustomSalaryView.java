package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lijian
 * @date 2020/12/7 10:37
 * @desc
 */
@Data
public class UserCustomSalaryView {
    private Integer type;
    private String typeStr;
    private String configName;
    private BigDecimal customSalary;
    private String remark;
}
