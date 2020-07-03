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
    ERROR(500, "错误");
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
