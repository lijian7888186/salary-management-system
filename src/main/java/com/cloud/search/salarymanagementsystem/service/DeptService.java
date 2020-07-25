package com.cloud.search.salarymanagementsystem.service;

import com.cloud.search.salarymanagementsystem.domain.Dept;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.views.DeptPageParam;
import com.cloud.search.salarymanagementsystem.domain.views.UserParam;
import com.github.pagehelper.Page;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/20 19:20
 * @desc
 */
public interface DeptService {
    List<Dept> findAll();
    ResponseView findByPage(DeptPageParam deptPageParam);
    void addDept(Dept dept);
    void deleteDept(Dept dept);
}
