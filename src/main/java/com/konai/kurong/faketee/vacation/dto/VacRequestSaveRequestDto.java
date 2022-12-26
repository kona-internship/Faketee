package com.konai.kurong.faketee.vacation.dto;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.vacation.entity.VacRequest;
import com.konai.kurong.faketee.vacation.entity.VacType;
import com.konai.kurong.faketee.vacation.util.RequestVal;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

import static com.konai.kurong.faketee.vacation.service.VacRequestService.DATETIMEFORMATTER;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class VacRequestSaveRequestDto {

    String date;
    VacType vacType;
    Draft draft;
    Employee employee;

    public VacRequest toEntity(){

        return VacRequest.builder()
                .date(LocalDate.parse(date, DATETIMEFORMATTER))
                .val(RequestVal.T)
                .vacType(vacType)
                .draft(draft)
                .employee(employee)
                .build();
    }

}
