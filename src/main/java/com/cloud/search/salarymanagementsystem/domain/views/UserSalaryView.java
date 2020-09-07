package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

import java.math.BigDecimal;

/**
 * @author lijian
 * @date 2020/8/5 17:04
 * @desc
 */
@Data
public class UserSalaryView {
    private BigDecimal salary;
    private String dt;
}
