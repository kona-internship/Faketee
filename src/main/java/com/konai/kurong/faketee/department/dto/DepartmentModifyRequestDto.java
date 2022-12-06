package com.konai.kurong.faketee.department.dto;

import com.konai.kurong.faketee.employee.utils.DepIdsDto;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Data
public class DepartmentModifyRequestDto implements DepIdsDto {
    private String name;
    private List<Long> lowDepartmentIdList;
    private List<Long> locationIdList;
    @NotNull
    private Boolean isModifyLow;

    @Override
    public List<Long> getDepIds() {
        return getLowDepartmentIdList();
    }
}
