package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.domain.Dept;
import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.UpdateGroups;
import com.cloud.search.salarymanagementsystem.domain.views.DeptPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.DeptParam;
import com.cloud.search.salarymanagementsystem.domain.views.OptionView;
import com.cloud.search.salarymanagementsystem.service.DeptService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author lijian
 * @date 2020/7/22 14:49
 * @desc
 */
@RestController
@Api(tags = "部门接口")
@Permission
@RequestMapping("dept")
@Slf4j
public class DeptController {
    @Resource
    private DeptService deptService;
    @PostMapping("findDeptOption")
    @ApiOperation(value = "查找部门名称的下拉框")
    public ResponseView findDeptOption() {
        List<Dept> list = deptService.findAll();
        List<OptionView> collect = list.stream().map(view -> {
            OptionView optionView = new OptionView();
            optionView.setLabel(view.getDeptName());
            optionView.setValue(view.getId());
            return optionView;
        }).collect(Collectors.toList());
        ResponseView responseView = ResponseView.buildSuccess(collect);
        return responseView;
    }

    @PostMapping("findList")
    @ApiOperation(value = "查找部门")
    public ResponseView findList(@RequestBody @Validated DeptPageParam deptPageParam) {
        ResponseView responseView = deptService.findByPage(deptPageParam);
        return responseView;
    }

    @PostMapping("addDept")
    @ApiOperation(value = "添加部门")
    public ResponseView addDept(@RequestBody @Validated(InsertGroups.class) DeptParam deptParam) {
        Dept dept = new Dept();
        dept.setDeptName(deptParam.getDeptName());
        deptService.addDept(dept);
        return ResponseView.buildSuccess();
    }

    @PostMapping("deleteDept")
    @ApiOperation(value = "删除部门")
    public ResponseView deleteDept(@RequestBody @Validated(UpdateGroups.class) DeptParam deptParam) {
        Dept dept = new Dept();
        dept.setId(deptParam.getId());
        dept.setYn(0);
        deptService.deleteDept(dept);
        return ResponseView.buildSuccess();
    }

}
