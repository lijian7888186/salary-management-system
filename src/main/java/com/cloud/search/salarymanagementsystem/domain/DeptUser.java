package com.cloud.search.salarymanagementsystem.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;

/**
* 部门用户表
* @author 
*/
@Data
public class DeptUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 部门id
    */
    private Long detpId;

    /**
    * 用户id
    */
    private Long userId;

    /**
    * 用户在部门的职位
    */
    private Integer userLevel;

    /**
    * 创建时间
    */
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