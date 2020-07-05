package com.cloud.search.salarymanagementsystem.enums;

/**
 * @author lijian
 * @date 2020/6/29 10:53
 * @desc
 */
public enum ResponseEnum {
    /**
     * 成功
     */
    SUCCESS(200, "成功"),
    /**
     * 错误
     */
    ERROR(500, "错误"),
    /**
     * 没有登陆
     */
    NO_LOGIN(401, "没有登陆"),
    /**
     * 没有权限
     */
    NO_PERMISSION(403, "没有权限"),
    /**
     * 重定向
     */
    REDIRECT(302, "重定向");
    private Integer code;
    private String msg;

    ResponseEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }
}
