package com.konai.kurong.faketee.department.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
public class DepartmentSaveRequestDto {

    @NotBlank
    private String name;

    private Long superId;

    @NotNull
    private Long locationId;
}
