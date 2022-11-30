package com.konai.kurong.faketee.department.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
public class DepartmentSaveRequestDto {

    @NotBlank
    private String name;

    private Long superId;
    @NotNull
    private List<Long> locationIdList;
}

