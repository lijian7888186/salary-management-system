package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.LoginUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijian
 * @date 2020/7/1 18:49
 * @desc
 */
@RestController
@RequestMapping("login")
@Api(tags = "登陆接口")
public class LoginController {
    @PostMapping("toLogin")
    @ApiOperation(value = "登陆")
    public ResponseView toLogin(@RequestBody LoginUser loginUser) {
        return ResponseView.buildSuccess();
    }
}
