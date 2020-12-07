package com.cloud.search.salarymanagementsystem.mapper;

import com.cloud.search.salarymanagementsystem.domain.CustomSalaryConfig;
import com.cloud.search.salarymanagementsystem.domain.CustomSalaryManager;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryManagerPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserCustomSalaryView;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/23 18:28
 * @desc
 */
public interface CustomSalaryMapper extends BaseMapper<CustomSalaryManager> {

}
