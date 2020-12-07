package com.cloud.search.salarymanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import java.util.Date;

/**
* 用户薪资管理表
* @author 
*/
@Data
public class SalaryManager implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 部门id
    */
    private Long deptId;

    /**
    * 日期
    */
    private String dt;

    /**
    * 工资
    */
    private BigDecimal salary;

    /**
    * 工资配置id
    */
    private Long salaryConfigId;

    /**
    * 添加时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
    * 修改时间
    */
    private Date updateTime;

    /**
    * 是否有效
    */
    private Integer yn;


}