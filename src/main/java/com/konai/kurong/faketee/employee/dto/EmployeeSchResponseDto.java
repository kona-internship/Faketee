package com.konai.kurong.faketee.employee.dto;

import com.konai.kurong.faketee.employee.entity.Employee;
import lombok.*;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Builder
@Data
public class EmployeeSchResponseDto {
    private Long id;
    private String name;
    private String depName;
    private String posName;


    public static EmployeeSchResponseDto convertToDto(Employee employee){
        return EmployeeSchResponseDto.builder()
                .id(employee.getId())
                .name(employee.getName())
                .depName(employee.getDepartment().getName())
                .posName(employee.getPosition().getName())
                .build();
    }

    public static List<EmployeeSchResponseDto> convertToDtoList(List<Employee> employeeList){
        Stream<Employee> stream = employeeList.stream();

        return stream.map((employee) -> convertToDto(employee)).collect(Collectors.toList());
    }
}
