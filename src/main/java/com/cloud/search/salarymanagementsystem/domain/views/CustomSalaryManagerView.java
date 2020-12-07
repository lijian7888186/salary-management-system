package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.CustomSalaryManager;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
* 自定义用户薪资表
* @author 
*/
@Data
public class CustomSalaryManagerView extends CustomSalaryManager {
    private String userName;
    private String nickname;
}