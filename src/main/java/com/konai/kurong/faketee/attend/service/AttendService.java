package com.konai.kurong.faketee.attend.service;

import com.konai.kurong.faketee.attend.dto.AttendResponseDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.schedule.entity.ScheduleInfo;
import com.konai.kurong.faketee.schedule.service.ScheduleInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendService {
    private final ScheduleInfoService scheduleInfoService;
    private final EmployeeService employeeService;

    @Transactional
    public AttendResponseDto getUserScheduleInfo(Long corId, String date, Long empId) {
        ScheduleInfo scheduleInfo = scheduleInfoService.getSchByDateAndEmp(date, corId, empId);
        Employee emp = employeeService.findByEmployeeById(empId);
        String department = emp.getDepartment().getName();

        if (scheduleInfo == null) {
            return null;
        }
        return AttendResponseDto.builder()
                .startTime(scheduleInfo.getStartTime().toString())
                .endTime(scheduleInfo.getEndTime().toString())
                .depName(department)
                .build();
    }
}
