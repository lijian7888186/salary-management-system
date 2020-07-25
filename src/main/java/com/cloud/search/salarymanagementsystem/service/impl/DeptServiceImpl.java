package com.cloud.search.salarymanagementsystem.service.impl;

import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.config.UserInfoContext;
import com.cloud.search.salarymanagementsystem.domain.Dept;
import com.cloud.search.salarymanagementsystem.domain.DeptUser;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.DeptPageParam;
import com.cloud.search.salarymanagementsystem.mapper.DeptMapper;
import com.cloud.search.salarymanagementsystem.mapper.DeptUserMapper;
import com.cloud.search.salarymanagementsystem.service.DeptService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author lijian
 * @date 2020/7/22 14:54
 * @desc
 */
@Service
public class DeptServiceImpl implements DeptService {
    @Resource
    private DeptMapper deptMapper;
    @Resource
    private DeptUserMapper deptUserMapper;
    @Override
    public List<Dept> findAll() {
        Dept dept = new Dept();
        dept.setYn(1);
        List<Dept> list = deptMapper.select(dept);
        return list;
    }

    @Override
    public ResponseView findByPage(DeptPageParam deptPageParam) {
        Page<Dept> page = PageHelper.startPage(deptPageParam.getPage(), deptPageParam.getPageSize(), "id desc");
        Dept dept = new Dept();
        dept.setYn(1);
        deptMapper.select(dept);
        ResponseView responseView = ResponseView.buildSuccess(page.getResult());
        responseView.setTotal(page.getTotal());
        return responseView;
    }

    @Override
    public void addDept(Dept dept) {
        deptMapper.insertSelective(dept);
    }

    @Override
    public void deleteDept(Dept dept) {
        DeptUser deptUser = new DeptUser();
        deptUser.setDetpId(dept.getId());
        deptUser.setYn(1);
        int count = deptUserMapper.selectCount(deptUser);
        if (count > 0) {
            throw new RuntimeException("部门有员工,不能删除");
        }
        deptMapper.updateByPrimaryKeySelective(dept);
    }
}
