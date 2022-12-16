package com.konai.kurong.faketee.employee.dto;

import com.konai.kurong.faketee.employee.entity.Employee;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmployeeSessionResponseDto implements Serializable {

    private Long id;
    private Long corId;

    public static EmployeeSessionResponseDto convertToDto(Employee employee){

        return EmployeeSessionResponseDto.builder()
                .id(employee.getId())
                .corId(employee.getCorporation().getId())
                .build();
    }
}
