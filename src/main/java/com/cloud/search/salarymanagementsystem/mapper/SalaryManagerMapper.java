package com.cloud.search.salarymanagementsystem.mapper;

import com.cloud.search.salarymanagementsystem.domain.SalaryManager;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryManagerPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryManagerView;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/23 17:33
 * @desc
 */
public interface SalaryManagerMapper extends BaseMapper<SalaryManager> {
    List<SalaryManagerView> findByPage(SalaryManagerPageParam salaryManagerPageParam);
}
