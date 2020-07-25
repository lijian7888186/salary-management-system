package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.domain.CustomSalaryConfig;
import com.cloud.search.salarymanagementsystem.domain.Dept;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.views.CustomSalaryConfigView;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryConfigParam;
import com.cloud.search.salarymanagementsystem.mapper.CustomSalaryConfigMapper;
import com.cloud.search.salarymanagementsystem.mapper.DeptMapper;
import com.cloud.search.salarymanagementsystem.mapper.UserMapper;
import com.cloud.search.salarymanagementsystem.service.CustomSalaryConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author lijian
 * @date 2020/7/23 18:18
 * @desc
 */
@Service
public class CustomSalaryConfigServiceImpl implements CustomSalaryConfigService {
    @Resource
    private CustomSalaryConfigMapper customSalaryConfigMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DeptMapper deptMapper;
    @Override
    public ResponseView findByPage(SalaryConfigPageParam salaryConfigPageParam) {
        User user = new User();
        user.setYn(1);
        List<User> users = userMapper.select(user);
        Map<Long, String> userCollect = users.stream().collect(Collectors.toMap(User::getId, User::getUserName));
        Dept dept = new Dept();
        dept.setYn(1);
        List<Dept> depts = deptMapper.select(dept);
        Map<Long, String> deptCollect = depts.stream().collect(Collectors.toMap(Dept::getId, Dept::getDeptName));
        Page<CustomSalaryConfig> page = PageHelper.startPage(salaryConfigPageParam.getPage(), salaryConfigPageParam.getPageSize(), "id desc");
        CustomSalaryConfig config = new CustomSalaryConfig();
        config.setYn(1);
        List<CustomSalaryConfig> list = customSalaryConfigMapper.select(config);
        List<CustomSalaryConfigView> views = new ArrayList<>();
        list.forEach(view -> {
            CustomSalaryConfigView configView = new CustomSalaryConfigView();
            BeanUtils.copyProperties(view, configView);
            if (view.getDeptId() != null) {
                String string = deptCollect.get(view.getDeptId());
                configView.setDeptName(string);
            }
            if (view.getUserId() != null) {
                String string = userCollect.get(view.getUserId());
                configView.setUserName(string);
            }
            views.add(configView);
        });
        ResponseView responseView = ResponseView.buildSuccess(views);
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Override
    public void addConfig(SalaryConfigParam salaryConfigParam) {
        CustomSalaryConfig config = new CustomSalaryConfig();
        config.setCustomSalary(salaryConfigParam.getSalary());
        if (salaryConfigParam.getDeptId() == null && salaryConfigParam.getUserId() == null) {
            throw new RuntimeException("用户名称和部门名称不能都为空");
        }
        config.setDeptId(salaryConfigParam.getDeptId());
        config.setUserId(salaryConfigParam.getUserId());
        config.setName(salaryConfigParam.getName());
        customSalaryConfigMapper.insertSelective(config);
    }

    @Override
    public void deleteConfig(SalaryConfigParam salaryConfigParam) {
        CustomSalaryConfig config = new CustomSalaryConfig();
        config.setYn(0);
        config.setId(salaryConfigParam.getId());
        customSalaryConfigMapper.updateByPrimaryKeySelective(config);
    }
}
