package com.konai.kurong.faketee.schedule.dto;

import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Data
public class ScheduleInfoSaveRequestDto {
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    private List<String> dates;

    private Long tempId;

    private List<Long> checkedEmp;

    public List<LocalDate> transToDate(){
        List<LocalDate> dateTimeList = new ArrayList<>();

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        for(int i = 0; i < dates.size(); i++){
            LocalDate dateTime = LocalDate.parse(dates.get(i), formatter);
            dateTimeList.add(dateTime);
        }
        return dateTimeList;
    }
}
