package com.cloud.search.salarymanagementsystem.config;

import com.cloud.search.salarymanagementsystem.annotation.Permission;
import com.cloud.search.salarymanagementsystem.domain.views.PermissionUrlInfo;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.NameValueExpression;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.Resource;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @author lijian
 * @date 2020/7/21 15:25
 * @desc
 */
@Component
public class PermissionConfiguration implements ApplicationListener<ContextRefreshedEvent> {
    public static final List<PermissionUrlInfo> INFOS = new ArrayList<>();
    public static final Map<String, PermissionUrlInfo> INFO_MAP = new HashMap<>();
    @Resource
    private RequestMappingHandlerMapping mapping;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = mapping.getHandlerMethods();
        handlerMethods.forEach((k, v) -> {
            Permission permission = v.getMethodAnnotation(Permission.class);
            if (permission == null) {
                permission = v.getBeanType().getAnnotation(Permission.class);
            }
            if (permission == null) {
                return;
            }
            PatternsRequestCondition patternsCondition = k.getPatternsCondition();
            Set<String> patterns = patternsCondition.getPatterns();
            ApiOperation methodAnnotation = v.getMethodAnnotation(ApiOperation.class);
            Api annotation = v.getBeanType().getAnnotation(Api.class);
            String value = methodAnnotation.value();
            PermissionUrlInfo urlInfo = new PermissionUrlInfo();
            urlInfo.setUrl(patterns.stream().limit(1).collect(Collectors.joining()));
            urlInfo.setPrefix(annotation.tags()[0]);
            urlInfo.setSuffix(value);
            urlInfo.setName(urlInfo.getPrefix() + "/" + value);
            INFOS.add(urlInfo);
            INFO_MAP.put(urlInfo.getUrl(), urlInfo);
        });
    }
}
