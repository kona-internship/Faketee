package com.konai.kurong.faketee.department.dto;

import com.konai.kurong.faketee.employee.utils.DepIdsDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Getter
public class DepartmentSaveRequestDto implements DepIdsDto {

    @NotBlank
    private String name;

    private Long superId;
    @NotNull
    private List<Long> locationIdList;

    @Override
    public List<Long> getDepIds() {
        return getLocationIdList();
    }


//    public DepartmentSaveRequestDto(String name, Long superId, List<Long> locationIdList, List<Long> depIds) {
//        super(depIds);
//        this.name = name;
//        this.superId = superId;
//        this.locationIdList = locationIdList;
//    }
}

