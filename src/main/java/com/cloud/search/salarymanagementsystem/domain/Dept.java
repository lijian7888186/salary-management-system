package com.cloud.search.salarymanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;

/**
* 部门表
* @author 
*/
@Data
public class Dept implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 部门名称
    */
    private String deptName;

    /**
    * 添加时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date createTime;

    /**
    * 修改时间
    */
    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss", timezone = "GMT+8")
    private Date updateTime;

    /**
    * 是否有效
    */
    private Integer yn;


}