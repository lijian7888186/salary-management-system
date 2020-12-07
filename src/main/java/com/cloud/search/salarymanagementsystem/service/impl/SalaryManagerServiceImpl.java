package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.config.UserInfoContext;
import com.cloud.search.salarymanagementsystem.domain.*;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryManagerPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserCustomSalaryView;
import com.cloud.search.salarymanagementsystem.domain.views.UserSalaryView;
import com.cloud.search.salarymanagementsystem.enums.UserLevelEnum;
import com.cloud.search.salarymanagementsystem.mapper.*;
import com.cloud.search.salarymanagementsystem.service.CustomSalaryConfigService;
import com.cloud.search.salarymanagementsystem.service.SalaryManagerService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.time.*;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijian
 * @date 2020/7/23 17:32
 * @desc
 */
@Service
@Slf4j
public class SalaryManagerServiceImpl implements SalaryManagerService {
    @Resource
    private SalaryManagerMapper salaryManagerMapper;
    @Resource
    private UserMapper userMapper;
    @Resource
    private DeptUserMapper deptUserMapper;
    @Resource
    private SalaryConfigMapper salaryConfigMapper;
    @Resource
    private CustomSalaryConfigMapper customSalaryConfigMapper;

    @Resource
    private CustomSalaryMapper customSalaryMapper;

    @Override
    public ResponseView findByPage(SalaryManagerPageParam salaryManagerPageParam) {
        UserInfo userInfo = UserInfoContext.getUserInfo();
        salaryManagerPageParam.setDeptId(userInfo.getDeptId());
        if (UserLevelEnum.NORMAL.getLevel().equals(userInfo.getUserLevel())) {
            salaryManagerPageParam.setUserId(userInfo.getUserId());
        } else {
            if (StringUtils.isNotBlank(salaryManagerPageParam.getUserName())) {
                User user = new User();
                user.setYn(1);
                user.setUserName(salaryManagerPageParam.getUserName());
                List<User> users = userMapper.select(user);
                if (!CollectionUtils.isEmpty(users)) {
                    DeptUser deptUser = new DeptUser();
                    deptUser.setYn(1);
                    deptUser.setUserId(users.get(0).getId());
                    deptUser.setDetpId(userInfo.getDeptId());
                    int count = deptUserMapper.selectCount(deptUser);
                    if (count > 0) {
                        salaryManagerPageParam.setUserId(users.get(0).getId());
                    }
                }
            }
        }
        Page<SalaryManager> page = PageHelper.startPage(salaryManagerPageParam.getPage(), salaryManagerPageParam.getPageSize());
        salaryManagerMapper.findByPage(salaryManagerPageParam);
        ResponseView responseView = ResponseView.buildSuccess(page.getResult());
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Scheduled(cron = "0 0 0 1 * ?")
    public void scheduledAddSalary() {
        addSalary(null);
    }

    @Override
    public ResponseView addSalary(String dt) {
        if (StringUtils.isBlank(dt)) {
            dt = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).minusMonths(1).toString();
        }
        SalaryManager salaryManager = new SalaryManager();
        salaryManager.setYn(1);
        salaryManager.setDt(dt);
        int count = salaryManagerMapper.selectCount(salaryManager);
        if (count > 0) {
            return ResponseView.buildSuccess();
        }
        DeptUser deptUser = new DeptUser();
        deptUser.setYn(1);
        List<DeptUser> deptUsers = deptUserMapper.select(deptUser);
        String managerDt = dt;
        deptUsers.forEach(view -> {
            PageHelper.startPage(1, 1, "id desc");
            SalaryConfig salaryConfig = new SalaryConfig();
            salaryConfig.setYn(1);
            salaryConfig.setUser_id(view.getUserId());
            List<SalaryConfig> salaryConfigs = salaryConfigMapper.select(salaryConfig);
            SalaryConfig baseConfig = salaryConfigs.get(0);
            BigDecimal bigDecimal = baseConfig.getBaseSalary();
            CustomSalaryManager customSalaryManager = new CustomSalaryManager();
            customSalaryManager.setUserId(view.getUserId());
            customSalaryManager.setDt(managerDt);
            List<CustomSalaryManager> managers = customSalaryMapper.select(customSalaryManager);
            for (CustomSalaryManager manager : managers) {
                bigDecimal = bigDecimal.add(manager.getCustomSalary());
            }
            SalaryManager manager = new SalaryManager();
            manager.setUserId(view.getUserId());
            manager.setDeptId(view.getDetpId());
            manager.setDt(managerDt);
            manager.setSalaryConfigId(baseConfig.getId());
            manager.setSalary(bigDecimal);
            manager.setYn(1);
            salaryManagerMapper.insertSelective(manager);
        });
        return ResponseView.buildSuccess();
    }

    @Override
    public ResponseView findUserSalary() {
        UserInfo userInfo = UserInfoContext.getUserInfo();
        SalaryManager salaryManager = new SalaryManager();
        salaryManager.setYn(1);
        salaryManager.setUserId(userInfo.getUserId());
        PageHelper.startPage(1, 12, "dt,id desc");
        List<SalaryManager> list = salaryManagerMapper.select(salaryManager);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseView.buildError("没有数据");
        }
        List<UserSalaryView> collect = list.stream().map(view -> {
            UserSalaryView salaryView = new UserSalaryView();
            salaryView.setSalary(view.getSalary());
            salaryView.setDt(view.getDt());
            return salaryView;
        }).collect(Collectors.toList());
        ResponseView responseView = ResponseView.buildSuccess(collect);
        return responseView;
    }

    @Override
    public ResponseView findUserCustomSalary(SalaryManagerPageParam salaryManagerPageParam) {
        CustomSalaryManager manager = new CustomSalaryManager();
        manager.setYn(1);
        manager.setUserId(salaryManagerPageParam.getUserId());
        if (StringUtils.isBlank(salaryManagerPageParam.getDt())) {
            throw new RuntimeException("日期错误");
        }
        List<UserCustomSalaryView> views = new ArrayList<>();
        SalaryConfig salaryConfig = new SalaryConfig();
        salaryConfig.setId(salaryManagerPageParam.getSalaryConfigId());
        SalaryConfig salaryConfigDb = salaryConfigMapper.selectOne(salaryConfig);
        UserCustomSalaryView salaryView = new UserCustomSalaryView();
        salaryView.setConfigName("基本工资");
        salaryView.setType(1);
        salaryView.setTypeStr("基本工资");
        salaryView.setCustomSalary(salaryConfigDb.getBaseSalary());
        views.add(salaryView);
        manager.setDt(salaryManagerPageParam.getDt());
        List<CustomSalaryManager> list = customSalaryMapper.select(manager);
        for (CustomSalaryManager customSalaryManager : list) {
            CustomSalaryConfig config = new CustomSalaryConfig();
            config.setId(customSalaryManager.getCustomSalaryConfigId());
            CustomSalaryConfig customSalaryConfig = customSalaryConfigMapper.selectOne(config);
            UserCustomSalaryView view = new UserCustomSalaryView();
            view.setType(customSalaryConfig.getType());
            view.setTypeStr(view.getType() == 2 ? "补贴" : "扣款");
            view.setConfigName(view.getConfigName());
            view.setCustomSalary(customSalaryManager.getCustomSalary());
            view.setRemark(customSalaryManager.getRemark());
            views.add(view);
        }
        views.sort(Comparator.comparingInt(UserCustomSalaryView::getType));
        ResponseView responseView = ResponseView.buildSuccess(views);
        return responseView;
    }
}
