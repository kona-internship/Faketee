package com.konai.kurong.faketee.department.dto;

import com.konai.kurong.faketee.employee.utils.AuthIdsDto;
import com.konai.kurong.faketee.employee.utils.EmpAuthCheckList;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class DepartmentRemoveRequestDto implements AuthIdsDto {
    List<Map<Long, Long>> removeDepList;

    @Override
    public EmpAuthCheckList getEmpAuthCheckList() {
        List<Long> idList = new ArrayList<>();
        for (Map<Long, Long> map : getRemoveDepList()) {
            map.forEach((id, level) -> {
                idList.add(id);
            });
        }
        return EmpAuthCheckList.builder()
                .idList(idList)
                .build();
    }
}
