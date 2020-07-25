package com.cloud.search.salarymanagementsystem.service;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigParam;

/**
 * @author lijian
 * @date 2020/7/23 18:17
 * @desc
 */
public interface CustomSalaryConfigService {
    ResponseView findByPage(SalaryConfigPageParam salaryConfigPageParam);
    void addConfig(SalaryConfigParam salaryConfigParam);
    void deleteConfig(SalaryConfigParam salaryConfigParam);
}
