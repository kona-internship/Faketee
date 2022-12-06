package com.konai.kurong.faketee.department.dto;

import com.konai.kurong.faketee.employee.utils.DepIdsDto;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Data
public class DepartmentRemoveRequestDto implements DepIdsDto {
    List<Map<Long, Long>> removeDepList;

    @Override
    public List<Long> getDepIds() {
        List<Long> idList = new ArrayList<>();
        for (Map<Long, Long> map : getRemoveDepList()) {
            map.forEach((id, level) -> {
                idList.add(id);
            });
        }
        return idList;
    }
}
