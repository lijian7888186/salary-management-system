package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.config.UserInfoContext;
import com.cloud.search.salarymanagementsystem.domain.*;
import com.cloud.search.salarymanagementsystem.domain.views.SalaryManagerPageParam;
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
        String[] arr = {"1.0", "1.1", "1.2", "1.5", "1.6", "1.65", "1.7", "1.75", "2.0"};
        if (StringUtils.isBlank(dt)) {
            dt = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth()).toString();
        }
        SalaryManager salaryManager = new SalaryManager();
        salaryManager.setYn(1);
        salaryManager.setDt(dt);
        int count = salaryManagerMapper.selectCount(salaryManager);
        if (count > 0) {
            return ResponseView.buildSuccess();
        }
        PageHelper.startPage(1, 1, "id desc");
        SalaryConfig salaryConfig = new SalaryConfig();
        salaryConfig.setYn(1);
        List<SalaryConfig> salaryConfigs = salaryConfigMapper.select(salaryConfig);
        SalaryConfig baseConfig = salaryConfigs.get(0);
        DeptUser deptUser = new DeptUser();
        deptUser.setYn(1);
        List<DeptUser> deptUsers = deptUserMapper.select(deptUser);
        String managerDt = dt;
        deptUsers.forEach(view -> {
            int year = LocalDate.now().getYear();
            Instant instant = view.getCreateTime().toInstant();
            ZoneId zoneId = ZoneId.systemDefault();
            LocalDateTime localDateTime = instant.atZone(zoneId).toLocalDateTime();
            int createYear = localDateTime.getYear();
            int i = year - createYear;
            BigDecimal bigDecimal = new BigDecimal(arr[i > arr.length ? arr.length : i]);
            CustomSalaryConfig config = new CustomSalaryConfig();
            config.setYn(1);
            config.setUserId(view.getUserId());
            List<CustomSalaryConfig> userConfigs = customSalaryConfigMapper.select(config);
            BigDecimal salary = null;
            SalaryManager manager = new SalaryManager();
            manager.setUserId(view.getUserId());
            manager.setDeptId(view.getDetpId());
            manager.setDt(managerDt);
            manager.setSalaryConfigId(baseConfig.getId());
            if (!CollectionUtils.isEmpty(userConfigs)) {
                CustomSalaryConfig userConfig = userConfigs.get(userConfigs.size() - 1);
                salary = bigDecimal.multiply(userConfig.getCustomSalary()).setScale(0, BigDecimal.ROUND_HALF_UP);
                manager.setCustomSalaryConfigId(userConfig.getId());
            } else {
                config.setUserId(null);
                config.setDeptId(view.getDetpId());
                List<CustomSalaryConfig> deptConfigs = customSalaryConfigMapper.select(config);
                if (!CollectionUtils.isEmpty(deptConfigs)) {
                    CustomSalaryConfig deptConfig = deptConfigs.get(deptConfigs.size() - 1);
                    salary = bigDecimal.multiply(deptConfig.getCustomSalary()).setScale(0, BigDecimal.ROUND_HALF_UP);
                    manager.setCustomSalaryConfigId(deptConfig.getId());
                }
            }
            if (salary != null) {
                salary = salary.add(bigDecimal.multiply(baseConfig.getBaseSalary()).setScale(0, BigDecimal.ROUND_HALF_UP));
            } else {
                salary = bigDecimal.multiply(baseConfig.getBaseSalary()).setScale(0, BigDecimal.ROUND_HALF_UP);
            }
            manager.setSalary(salary);
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
}
