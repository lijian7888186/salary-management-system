package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.OptionView;
import com.cloud.search.salarymanagementsystem.enums.UserLevelEnum;
import com.cloud.search.salarymanagementsystem.service.SalaryManagerService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.time.LocalDate;
import java.time.temporal.TemporalAdjusters;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @author lijian
 * @date 2020/7/22 14:59
 * @desc
 */
@RestController
@Api(tags = "公共数据接口")
@RequestMapping("common")
@Slf4j
public class CommonController {
    @Resource
    private SalaryManagerService salaryManagerService;
    @PostMapping("findUserLevelList")
    @ApiOperation(value = "查找用户职位列表")
    public ResponseView findUserLevelList() {
        ResponseView responseView = ResponseView.buildSuccess(UserLevelEnum.LIST);
        return responseView;
    }

    @PostMapping("findDtList")
    @ApiOperation(value = "查找日期列表")
    public ResponseView findDtList(Integer num) {
        LocalDate now = LocalDate.now().with(TemporalAdjusters.firstDayOfMonth());
        List<OptionView> list = new ArrayList<>();
        OptionView view = new OptionView("全部", "");
        list.add(view);
        for (int i = 0; i < num; i++) {
            LocalDate localDate = now.minusMonths(i);
            OptionView optionView = new OptionView(localDate.toString(), localDate.toString());
            list.add(optionView);
        }
        ResponseView responseView = ResponseView.buildSuccess(list);
        return responseView;
    }

    @PostMapping("addSalary")
    @ApiOperation(value = "添加工资数据")
    public ResponseView addSalary(String dt) {
        ResponseView responseView = salaryManagerService.addSalary(dt);
        return responseView;
    }

}
