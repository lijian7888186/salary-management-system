package com.cloud.search.salarymanagementsystem.domain.views;

import lombok.Data;

/**
 * @author lijian
 * @date 2020/7/21 15:58
 * @desc
 */
@Data
public class PermissionUrlInfo {
    private String url;
    private String name;
    private String prefix;
    private String suffix;
}
