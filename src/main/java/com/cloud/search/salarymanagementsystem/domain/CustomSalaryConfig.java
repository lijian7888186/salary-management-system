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
* 自定义用户薪资表配置表
* @author 
*/
@Data
public class CustomSalaryConfig implements Serializable {

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
    * 自定义工资(有自定义工资以自定义工资为准)
    */
    private BigDecimal customSalary;

    /**
     * 工资说明
     */
    private String name;

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