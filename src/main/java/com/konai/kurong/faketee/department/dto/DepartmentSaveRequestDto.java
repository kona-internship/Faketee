package com.konai.kurong.faketee.department.dto;

import com.konai.kurong.faketee.employee.utils.DepIdsDto;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSaveRequestDto implements DepIdsDto {

    @NotBlank
    private String name;

    private Long superId;
    @NotNull
    private List<Long> locationIdList;

    @Override
    public List<Long> getDepIds() {
        List<Long> superIds = new ArrayList<>();
        superIds.add(getSuperId());
        return superIds;
    }


//    public DepartmentSaveRequestDto(String name, Long superId, List<Long> locationIdList, List<Long> depIds) {
//        super(depIds);
//        this.name = name;
//        this.superId = superId;
//        this.locationIdList = locationIdList;
//    }
}

