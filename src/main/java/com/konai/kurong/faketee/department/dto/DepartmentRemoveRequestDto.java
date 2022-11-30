package com.konai.kurong.faketee.department.dto;

import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
public class DepartmentRemoveRequestDto {
    List<Map<Long, Long>> removeDepList;
}
