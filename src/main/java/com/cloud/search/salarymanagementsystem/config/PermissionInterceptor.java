package com.cloud.search.salarymanagementsystem.config;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class PermissionInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Permission methodAnnotation = handlerMethod.getMethodAnnotation(Permission.class);
            if (methodAnnotation == null) {
                Permission annotation = handlerMethod.getBeanType().getAnnotation(Permission.class);
                if (annotation == null) {
                    String requestURI = request.getRequestURI();
                }
            }

        }
        return true;
    }
}
