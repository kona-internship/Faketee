package com.konai.kurong.faketee.schedule.dto;

import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
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
    private TemplateResponseDto template;
    private DepartmentResponseDto department;

    @QueryProjection
    public TemplateDepartmentResponseDto(TemplateDepartment templateDepartment){

        this.id = templateDepartment.getId();
        this.template = new TemplateResponseDto(templateDepartment.getTemplate());
        this.department =  DepartmentResponseDto.convertToDto(templateDepartment.getDepartment());
    }

    public static TemplateDepartmentResponseDto convertToDto(TemplateDepartment templateDepartment){

        return TemplateDepartmentResponseDto.builder()
                .id(templateDepartment.getId())
                .template(TemplateResponseDto.convertToDto(templateDepartment.getTemplate()))
                .department(DepartmentResponseDto.convertToDto(templateDepartment.getDepartment()))
                .build();
    }
}
