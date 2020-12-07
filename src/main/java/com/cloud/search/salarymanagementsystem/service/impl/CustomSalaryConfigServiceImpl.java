package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.domain.*;
import com.cloud.search.salarymanagementsystem.domain.views.*;
import com.cloud.search.salarymanagementsystem.mapper.CustomSalaryConfigMapper;
import com.cloud.search.salarymanagementsystem.mapper.CustomSalaryMapper;
import com.cloud.search.salarymanagementsystem.mapper.DeptMapper;
import com.cloud.search.salarymanagementsystem.mapper.UserMapper;
import com.cloud.search.salarymanagementsystem.service.CustomSalaryConfigService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    @Resource
    private CustomSalaryMapper customSalaryMapper;
    @Override
    public ResponseView findByPage(SalaryConfigPageParam salaryConfigPageParam) {
        Page<CustomSalaryConfig> page = PageHelper.startPage(salaryConfigPageParam.getPage(), salaryConfigPageParam.getPageSize(), "id desc");
        CustomSalaryConfig config = new CustomSalaryConfig();
        config.setYn(1);
        List<CustomSalaryConfig> list = customSalaryConfigMapper.select(config);
        List<CustomSalaryConfigView> views = new ArrayList<>();
        list.forEach(view -> {
            CustomSalaryConfigView configView = new CustomSalaryConfigView();
            BeanUtils.copyProperties(view, configView);
            configView.setTypeStr(view.getType() == 2 ? "补贴" : "扣款");
            views.add(configView);
        });
        ResponseView responseView = ResponseView.buildSuccess(views);
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Override
    public void addConfig(SalaryConfigParam salaryConfigParam) {
        CustomSalaryConfig salaryConfig = new CustomSalaryConfig();
        salaryConfig.setYn(1);
        salaryConfig.setCustomSalaryName(salaryConfigParam.getCustomSalaryName());
        int count = customSalaryConfigMapper.selectCount(salaryConfig);
        if (count > 0) {
            throw new RuntimeException("自定义配置名称不能重复");
        }
        CustomSalaryConfig config = new CustomSalaryConfig();
        config.setType(salaryConfigParam.getType());
        config.setCustomSalaryName(salaryConfigParam.getCustomSalaryName());
        config.setRemark(salaryConfigParam.getRemark());
        customSalaryConfigMapper.insertSelective(config);
    }

    @Override
    public void deleteConfig(SalaryConfigParam salaryConfigParam) {
        CustomSalaryConfig config = new CustomSalaryConfig();
        config.setYn(0);
        config.setId(salaryConfigParam.getId());
        customSalaryConfigMapper.updateByPrimaryKeySelective(config);
    }

    @Override
    public void addUserCustomSalary(UserCustomSalaryParam userCustomSalaryParam) {
        CustomSalaryManager manager = new CustomSalaryManager();
        LocalDate parse;
        try {
            parse = LocalDate.parse(userCustomSalaryParam.getDt(), DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        } catch (Exception e) {
            throw new RuntimeException("日期错误,只能输入未来日期的每个月的1号");
        }
        if (parse.isBefore(LocalDate.now())) {
            throw new RuntimeException("日期错误,只能输入未来日期的每个月的1号");
        }
        if (parse.getDayOfMonth() != 1) {
            throw new RuntimeException("日期错误,只能输入未来日期的每个月的1号");
        }

        manager.setUserId(userCustomSalaryParam.getUserId());
        manager.setCustomSalaryConfigId(userCustomSalaryParam.getCustomSalaryConfigId());
        manager.setCustomSalary(userCustomSalaryParam.getCustomSalary());
        manager.setDt(userCustomSalaryParam.getDt());
        manager.setYn(1);
        customSalaryMapper.insert(manager);
    }

    @Override
    public void deleteUserCustomSalary(UserCustomSalaryParam userCustomSalaryParam) {
        CustomSalaryManager manager = new CustomSalaryManager();
        manager.setId(userCustomSalaryParam.getId());
        manager.setYn(0);
        customSalaryMapper.updateByPrimaryKeySelective(manager);
    }

    @Override
    public ResponseView findUserCustomSalaryList(UserCustomSalaryParam userCustomSalaryParam) {
        List<CustomSalaryManagerView> views = new ArrayList<>();
        CustomSalaryManager manager = new CustomSalaryManager();
        manager.setYn(1);
        manager.setCustomSalaryConfigId(userCustomSalaryParam.getId());
        List<CustomSalaryManager> list = customSalaryMapper.select(manager);
        for (CustomSalaryManager customSalaryManager : list) {
            CustomSalaryManagerView view = new CustomSalaryManagerView();
            User user = new User();
            user.setId(customSalaryManager.getUserId());
            User userDb = userMapper.selectOne(user);
            BeanUtils.copyProperties(customSalaryManager, view);
            view.setUserName(userDb.getUserName());
            view.setNickname(userDb.getNickname());
            views.add(view);
        }
        return ResponseView.buildSuccess(views);
    }
}
