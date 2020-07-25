package com.cloud.search.salarymanagementsystem.domain;

import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;
import java.util.Date;

/**
* 权限表
* @author 
*/
@Data
public class Permission implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 权限url
    */
    private String permissionUrl;

    /**
    * 权限描述
    */
    private String permissionMsg;

    /**
    * 添加时间
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