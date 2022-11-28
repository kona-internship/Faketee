package com.konai.kurong.faketee.department.dto;

import com.konai.kurong.faketee.department.entity.Department;
import com.konai.kurong.faketee.position.dto.PositionResponseDto;
import com.konai.kurong.faketee.position.entity.Position;
import lombok.Builder;
import lombok.Data;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Data
@Builder
public class DepartmentResponseDto {

    private Long id;
    private String name;
    private Long level;
    private Long superId;

    // 쿼리 확인 필요(연관관계 매핑된거의 id를 가져오면서 어떻게 쿼리 동작하는지)
    public static DepartmentResponseDto convertToDto(Department department){
        return DepartmentResponseDto.builder()
                .id(department.getId())
                .name(department.getName())
                .level(department.getLevel())
                .superId((department.getSuperDepartment()!= null? department.getSuperDepartment().getId(): null))
                .build();
    }

    public static List<DepartmentResponseDto> convertToDtoList(List<Department> departmentList) {
        Stream<Department> stream = departmentList.stream();

        return stream.map((department) -> convertToDto(department)).collect(Collectors.toList());
    }
}
