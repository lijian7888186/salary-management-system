package com.cloud.search.salarymanagementsystem.config;

import com.alibaba.fastjson.JSON;
import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.domain.ResponseView;
import com.cloud.search.salarymanagementsystem.enums.ResponseEnum;
import com.cloud.search.salarymanagementsystem.service.RolePermissionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
public class PermissionInterceptor implements HandlerInterceptor {
    @Resource
    private RolePermissionService rolePermissionService;
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (handler instanceof HandlerMethod) {
            HandlerMethod handlerMethod = (HandlerMethod) handler;
            Permission methodAnnotation = handlerMethod.getMethodAnnotation(Permission.class);
            if (methodAnnotation == null) {
                methodAnnotation = handlerMethod.getBeanType().getAnnotation(Permission.class);
            }
            if (methodAnnotation != null) {
                String url = request.getRequestURI();
                if (rolePermissionService.checkPermission(url)) {
                    return true;
                }
                returnNoPermission(response);
                return false;
            }

        }
        return true;
    }

    private void returnNoPermission(HttpServletResponse response) {
        response.setHeader("Content-type", "application/json; charset=utf-8");
        ResponseView responseView = ResponseView.buildError(ResponseEnum.NO_PERMISSION);
        try {
            ServletOutputStream outputStream = response.getOutputStream();
            outputStream.write(JSON.toJSONString(responseView).getBytes("utf-8"));
        } catch (IOException e) {
            log.error("response getOutputStream error", e);
        }
    }

}
