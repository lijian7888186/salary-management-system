package com.cloud.search.salarymanagementsystem.controller;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.config.UserInfo;
import com.cloud.search.salarymanagementsystem.config.UserInfoContext;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.domain.User;
import com.cloud.search.salarymanagementsystem.domain.views.LoginUser;
import com.cloud.search.salarymanagementsystem.service.UserService;
import com.cloud.search.salarymanagementsystem.utils.CacheUtil;
import com.cloud.search.salarymanagementsystem.utils.Consts;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.ehcache.Cache;
import org.springframework.beans.BeanUtils;
import org.springframework.util.CollectionUtils;
import org.springframework.util.DigestUtils;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;
import java.util.UUID;

/**
 * @author lijian
 * @date 2020/7/1 18:49
 * @desc
 */
@RestController
@RequestMapping("login")
@Api(tags = "登陆接口")
@Slf4j
public class LoginController {
    @Resource
    private UserService userService;
    @PostMapping("toLogin")
    @ApiOperation(value = "登陆")
    public ResponseView toLogin(@Validated @RequestBody LoginUser loginUser, BindingResult bindingResult, HttpServletRequest request, HttpServletResponse response) {
        if (bindingResult.getErrorCount() != 0) {
            return ResponseView.buildError("用户名或密码不能为空");
        }
        User user = new User();
        BeanUtils.copyProperties(loginUser, user);
        user.setYn(1);
        try {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes("utf-8")));
        } catch (UnsupportedEncodingException e) {
            log.error("toLogin error", e);
            throw new RuntimeException("系统错误");
        }
        List<User> list = userService.findList(user);
        if (CollectionUtils.isEmpty(list)) {
            return ResponseView.buildError("用户名或密码错误");
        }
        String userName = list.get(0).getUserName();
        Long id = list.get(0).getId();
        String string = UUID.randomUUID().toString();
        Cache<String, String> stringCache = CacheUtil.createStringCache(string);
        stringCache.put(Consts.USER_NAME, userName);
        stringCache.put(Consts.USER_ID, String.valueOf(id));
        Cookie cookie = new Cookie(Consts.LOING_COOKIE_NAMW, string);
        cookie.setHttpOnly(true);
        cookie.setPath("/");
        cookie.setMaxAge(60 * 60 * 75);
        cookie.setDomain(request.getServerName());
        response.addCookie(cookie);
        Cache<String, String> cache = CacheUtil.getStringCache(Consts.UPDATE_PASSWORD);
        if (cache != null) {
            if (cache.get(id.toString()) != null) {
                cache.remove(id.toString());
            }
        }
        return ResponseView.buildSuccess();
    }

    @PostMapping("checkLogin")
    @ApiOperation(value = "是否登录")
    public ResponseView checkLogin(HttpServletRequest request) {
        return ResponseView.buildSuccess();
    }

    @PostMapping("getLoginUser")
    @ApiOperation(value = "获取登录用户信息")
    public ResponseView getLoginUser() {
        UserInfo userInfo = UserInfoContext.getUserInfo();
        ResponseView responseView = ResponseView.buildSuccess(userInfo.getUserName());
        return responseView;
    }

    @PostMapping("logout")
    @ApiOperation(value = "登出")
    public ResponseView logout() {
        UserInfo userInfo = UserInfoContext.getUserInfo();
        CacheUtil.deleteCache(userInfo.getCookieName());
        return ResponseView.buildSuccess();
    }

}
