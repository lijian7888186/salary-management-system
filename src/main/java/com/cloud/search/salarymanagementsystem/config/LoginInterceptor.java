package com.cloud.search.salarymanagementsystem.config;

import com.alibaba.fastjson.JSON;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.enums.ResponseEnum;
import com.cloud.search.salarymanagementsystem.service.UserService;
import com.cloud.search.salarymanagementsystem.utils.CacheUtil;
import com.cloud.search.salarymanagementsystem.utils.Consts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.ehcache.CacheManager;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * @author lijian
 * @date 2020/7/1 19:41
 * @desc
 */
@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Resource
    public UserService userService;

    private static String[] excludeUrl = {"swagger-resources"};

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            boolean loginUrl = isLoginUrl(request);
            if (loginUrl) {
                return true;
            }
            if (checkExcludeUrl(request)) {
                return true;
            }
            Cookie[] cookies = request.getCookies();
            if (Objects.isNull(cookies)) {
                returnToLogin(response);
                return false;
            }
            for (Cookie cookie : cookies) {
                if (Consts.LOING_COOKIE_NAMW.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    Cache<String, String> cache = CacheUtil.getStringCache(value);
                    if (Objects.isNull(cache)) {
                        returnToLogin(response);
                        return false;
                    }
                    String userName = cache.get(Consts.USER_NAME);
                    String id = cache.get(Consts.USER_ID);
                    if (Objects.isNull(userName) || Objects.isNull(id)) {
                        returnToLogin(response);
                        CacheUtil.deleteCache(value);
                        return false;
                    }
                    Cache<String, String> stringCache = CacheUtil.getStringCache(Consts.UPDATE_PASSWORD);
                    if (stringCache != null) {
                        String string = stringCache.get(id);
                        if (string != null) {
                            returnToLogin(response);
                            CacheUtil.deleteCache(value);
                            stringCache.remove(id);
                            return false;
                        }
                    }
                    UserInfo userInfo = new UserInfo();
                    userInfo.setUserName(userName);
                    userInfo.setUserId(Long.valueOf(id));
                    userInfo.setCookieName(value);
                    userService.assignmentUser(userInfo);
                    UserInfoContext.setUserInfo(userInfo);
                    return true;
                }
            }
            returnToLogin(response);
            return false;
        }
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        log.info("reomve user info");
        UserInfoContext.remove();
    }

    private boolean checkExcludeUrl(HttpServletRequest request) {
        String requestURI = request.getRequestURI();
        for (String url : excludeUrl) {
            if (requestURI.contains(url)) {
                return true;
            }
        }
        return false;
    }


    private boolean isLoginUrl(HttpServletRequest request) {
        if (request.getRequestURL().toString().endsWith("toLogin")) {
            return true;
        }
        return false;
    }

    private void returnToLogin(HttpServletResponse response) {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        ResponseView responseView = ResponseView.buildError(ResponseEnum.NO_LOGIN);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(JSON.toJSONString(responseView).getBytes("utf-8"));
        } catch (IOException e) {
            log.error("response getOutputStream error", e);
        }
    }

}
