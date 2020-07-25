package com.cloud.search.salarymanagementsystem.domain.views;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;

/**
 * @author lijian
 * @date 2020/7/1 18:50
 * @desc
 */
@Data
public class LoginUser {
    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空")
    private String userName;
    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码")
    private String password;
}
