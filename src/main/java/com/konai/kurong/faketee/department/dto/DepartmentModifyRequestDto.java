package com.konai.kurong.faketee.department.dto;

import com.konai.kurong.faketee.employee.utils.AuthIdsDto;
import com.konai.kurong.faketee.employee.utils.EmpAuthCheckList;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DepartmentModifyRequestDto implements AuthIdsDto {
    private String name;
    private List<Long> lowDepartmentIdList;
    private List<Long> locationIdList;
    @NotNull
    private Boolean isModifyLow;

    @Override
    public EmpAuthCheckList getEmpAuthCheckList() {
        return EmpAuthCheckList.builder()
                .idList(getLowDepartmentIdList())
                .build();
    }
}
