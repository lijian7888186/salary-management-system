package com.cloud.search.salarymanagementsystem.config;

import com.alibaba.fastjson.JSON;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.enums.ResponseEnum;
import com.cloud.search.salarymanagementsystem.utils.Consts;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
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
    public Cache<String, String> cache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            boolean loginUrl = isLoginUrl(request);
            Cookie[] cookies = request.getCookies();
            boolean flag = false;
            if (Objects.isNull(cookies)) {
                returnToLogin(response);
                return false;
            }
            for (Cookie cookie : cookies) {
                if (Consts.LOING_COOKIE_NAMW.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    String s = cache.get(value);
                    if (StringUtils.isBlank(s)) {
                        if (loginUrl) {
                            return true;
                        } else {
                            returnToLogin(response);
                            return false;
                        }
                    } else {
                        return true;
                    }
                } else {
                    if (request.getRequestURL().toString().endsWith("toLogin")) {
                        return true;
                    } else {
                        returnToLogin(response);
                        return false;
                    }
                }
            }
        }
        return true;
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
