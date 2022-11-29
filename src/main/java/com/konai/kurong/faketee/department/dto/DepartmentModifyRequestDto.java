package com.konai.kurong.faketee.department.dto;

import lombok.Data;

import java.util.List;

@Data
public class DepartmentModifyRequestDto {
    private String name;
    private List<Long> lowDepartmentIdList;
    private List<Long> locationIdList;
    private Boolean isModifyLow;
}
