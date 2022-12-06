package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.entity.TemplateDepartment;
import com.querydsl.core.annotations.QueryProjection;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TemplateDepartmentResponseDto {

    private Long id;
    private Template template;
    private Department department;

    @QueryProjection
    public TemplateDepartmentResponseDto(TemplateDepartment templateDepartment){

        this.id = templateDepartment.getId();
        this.template = templateDepartment.getTemplate();
        this.department = templateDepartment.getDepartment();
    }
}
