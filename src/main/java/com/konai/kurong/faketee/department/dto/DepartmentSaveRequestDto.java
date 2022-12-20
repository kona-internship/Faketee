package com.konai.kurong.faketee.department.dto;

import com.konai.kurong.faketee.employee.utils.AuthIdsDto;
import com.konai.kurong.faketee.employee.utils.EmpAuthCheckList;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentSaveRequestDto implements AuthIdsDto {

    @NotBlank
    private String name;

    private Long superId;
    @NotNull
    private List<Long> locationIdList;

    @Override
    public EmpAuthCheckList getEmpAuthCheckList() {
        List<Long> superIds = new ArrayList<>();
        superIds.add(getSuperId());
        return EmpAuthCheckList.builder()
                .idList(superIds)
                .build();
    }


//    public DepartmentSaveRequestDto(String name, Long superId, List<Long> locationIdList, List<Long> depIds) {
//        super(depIds);
//        this.name = name;
//        this.superId = superId;
//        this.locationIdList = locationIdList;
//    }
}