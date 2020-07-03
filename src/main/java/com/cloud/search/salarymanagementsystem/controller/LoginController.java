package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.views.LoginUser;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author lijian
 * @date 2020/7/1 18:49
 * @desc
 */
@RestController()
@RequestMapping("login")
public class LoginController {
    @RequestMapping("toLogin")
    public ResponseView toLogin(@RequestBody LoginUser loginUser) {
        return ResponseView.buildSuccess();
    }
}
