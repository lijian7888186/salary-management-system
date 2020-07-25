package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.SalaryConfig;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigParam;
import com.cloud.search.salarymanagementsystem.mapper.SalaryConfigMapper;
import com.cloud.search.salarymanagementsystem.service.SalaryConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author lijian
 * @date 2020/7/23 18:18
 * @desc
 */
@Service
public class SalaryConfigServiceImpl implements SalaryConfigService {
    @Resource
    private SalaryConfigMapper salaryConfigMapper;
    @Override
    public ResponseView findByPage(SalaryConfigPageParam salaryConfigPageParam) {
        Page<SalaryConfig> page = PageHelper.startPage(salaryConfigPageParam.getPage(), salaryConfigPageParam.getPageSize(), "id desc");
        SalaryConfig salaryConfig = new SalaryConfig();
        salaryConfig.setYn(1);
        salaryConfigMapper.select(salaryConfig);
        ResponseView responseView = ResponseView.buildSuccess(page.getResult());
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Override
    public void addConfig(SalaryConfigParam salaryConfigParam) {
        SalaryConfig salaryConfig = new SalaryConfig();
        salaryConfig.setBaseSalary(salaryConfigParam.getSalary());
        salaryConfigMapper.insertSelective(salaryConfig);
    }

    @Override
    public void deleteConfig(SalaryConfigParam salaryConfigParam) {
        SalaryConfig salaryConfig = new SalaryConfig();
        salaryConfig.setYn(0);
        salaryConfig.setId(salaryConfigParam.getId());
        salaryConfigMapper.updateByPrimaryKeySelective(salaryConfig);
    }

}
