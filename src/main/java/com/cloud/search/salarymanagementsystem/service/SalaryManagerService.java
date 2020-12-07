package com.cloud.search.salarymanagementsystem.service;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryManagerPageParam;

/**
 * @author lijian
 * @date 2020/7/23 17:32
 * @desc
 */
public interface SalaryManagerService {
    ResponseView findByPage(SalaryManagerPageParam salaryManagerPageParam);
    ResponseView addSalary(String dt);
    ResponseView findUserSalary();
    ResponseView findUserCustomSalary(SalaryManagerPageParam salaryManagerPageParam);
}
