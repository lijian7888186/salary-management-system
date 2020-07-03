package com.cloud.search.salarymanagementsystem.config;

import com.cloud.search.salarymanagementsystem.utils.Consts;
import org.apache.commons.lang3.StringUtils;
import org.ehcache.Cache;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Arrays;

/**
 * @author lijian
 * @date 2020/7/1 19:41
 * @desc
 */
public class LoginInterceptor implements HandlerInterceptor {
    @Resource
    public Cache<String, String> cache;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (handler instanceof HandlerMethod) {
            Cookie[] cookies = request.getCookies();
            boolean flag = false;
            for (Cookie cookie : cookies) {
                if (Consts.LOING_COOKIE_NAMW.equals(cookie.getName())) {
                    String value = cookie.getValue();
                    String s = cache.get(value);
                    if (StringUtils.isBlank(s)) {
                        if (request.getRequestURL().toString().endsWith("toLogin")) {
                            return true;
                        } else {
                            return false;
                        }
                    } else {
                        return true;
                    }
                } else {
                    if (request.getRequestURL().toString().endsWith("toLogin")) {
                        return true;
                    } else {
                        return false;
                    }
                }
            }
        }
        return false;
    }
}
