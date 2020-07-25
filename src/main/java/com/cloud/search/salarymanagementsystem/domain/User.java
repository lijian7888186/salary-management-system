package com.cloud.search.salarymanagementsystem.domain;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.util.Date;

/**
* Users and global privileges
* @author 
*/
@Data
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
    * id
    */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
    * 用户名
    */
    private String userName;

    /**
    * 密码
    */
    private String password;

    /**
    * 手机号
    */
    private String phone;

    /**
    * 创建时间
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