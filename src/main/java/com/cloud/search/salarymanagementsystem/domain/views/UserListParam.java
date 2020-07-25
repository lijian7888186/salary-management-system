package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.User;
import lombok.Data;

import java.util.List;

/**
 * @author lijian
 * @date 2020/7/21 15:08
 * @desc
 */
@Data
public class UserListParam extends User {
    private List<Long> userIdList;
}
