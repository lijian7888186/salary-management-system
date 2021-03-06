package com.cloud.search.salarymanagementsystem.domain.views;

import com.cloud.search.salarymanagementsystem.domain.DeleteGroups;
import com.cloud.search.salarymanagementsystem.domain.InsertGroups;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * @author lijian
 * @date 2020/7/23 19:08
 * @desc
 */
@Data
public class SalaryConfigParam {
    @NotNull(groups = DeleteGroups.class)
    private Long id;

    @NotNull(groups = InsertGroups.class)
    private String customSalaryName;

    private String remark;

    private Integer type;

    private BigDecimal salary;

}
