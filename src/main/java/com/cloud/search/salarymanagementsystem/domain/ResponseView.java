package com.cloud.search.salarymanagementsystem.domain;

import com.cloud.search.salarymanagementsystem.enums.ResponseEnum;
import lombok.Data;

/**
 * @author lijian
 * @date 2020/6/29 10:51
 * @desc
 */
@Data
public class ResponseView<T> {
    private Integer code;
    private String msg;
    private T data;

    public static ResponseView buildSuccess() {
        ResponseView responseView = new ResponseView();
        responseView.code = ResponseEnum.SUCCESS.getCode();
        responseView.msg = ResponseEnum.SUCCESS.getMsg();
        return responseView;
    }
    public static <T> ResponseView buildSuccess(T data) {
        ResponseView responseView = new ResponseView();
        responseView.code = ResponseEnum.SUCCESS.getCode();
        responseView.msg = ResponseEnum.SUCCESS.getMsg();
        responseView.data = data;
        return responseView;
    }

    public static ResponseView buildError(String msg) {
        ResponseView responseView = new ResponseView();
        responseView.code = ResponseEnum.ERROR.getCode();
        responseView.msg = msg;
        return responseView;
    }

}
