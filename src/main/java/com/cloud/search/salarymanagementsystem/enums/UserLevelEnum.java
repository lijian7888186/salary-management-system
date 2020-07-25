package com.cloud.search.salarymanagementsystem.enums;

import com.cloud.search.salarymanagementsystem.domain.views.OptionView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author lijian
 * @date 2020/7/21 14:43
 * @desc
 */
public enum UserLevelEnum {
    /**
     * 普通员工
     */
    NORMAL(1, "普通员工"),
    /**
     * 部门管理员
     */
    MANAGER(2, "部门管理员");
    private Integer level;
    private String name;

    public static final Map<Integer, String> MAP = new HashMap<>();

    public static final List<OptionView> LIST = new ArrayList<>();

    static {
        for (UserLevelEnum userLevelEnum : UserLevelEnum.values()) {
            MAP.put(userLevelEnum.getLevel(), userLevelEnum.getName());
            LIST.add(new OptionView(userLevelEnum.getName(), userLevelEnum.getLevel()));
        }
    }

    UserLevelEnum(Integer level, String name) {
        this.level = level;
        this.name = name;
    }

    public Integer getLevel() {
        return level;
    }

    public void setLevel(Integer level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
