package com.konai.kurong.faketee.schedule.dto.test;

import com.konai.kurong.faketee.department.dto.DepartmentResponseDto;
import com.konai.kurong.faketee.schedule.dto.TemplateResponseDto;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.entity.TemplateDepartment;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TempDepDto {
    private Long id;
    private DepartmentResponseDto department;

    public static TempDepDto convertToDto (TemplateDepartment templateDepartment){
        return TempDepDto.builder()
                .id(templateDepartment.getId())
                .department(DepartmentResponseDto.convertToDto(templateDepartment.getDepartment()))
                .build();
    }
    public static List<TempDepDto> convertToDtoList(List<TemplateDepartment> templateDepartmentList){
        Stream<TemplateDepartment> stream = templateDepartmentList.stream();

        return stream.map(templateDepartment -> convertToDto(templateDepartment)).collect(Collectors.toList());
    }
}
