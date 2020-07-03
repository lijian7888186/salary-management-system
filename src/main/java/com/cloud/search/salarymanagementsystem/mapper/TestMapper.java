package com.cloud.search.salarymanagementsystem.mapper;

import com.cloud.search.salarymanagementsystem.domain.Test;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;
import java.util.Map;

/**
 * @author lijian
 * @date 2020/3/17 21:08
 * @desc
 */
public interface TestMapper extends BaseMapper<Test> {
    List<Test> findTest(Map<String, String> map);
}
