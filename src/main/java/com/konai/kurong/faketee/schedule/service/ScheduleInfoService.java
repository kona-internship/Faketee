package com.konai.kurong.faketee.schedule.service;

import com.konai.kurong.faketee.employee.dto.EmployeeSchResponseDto;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.schedule.dto.ScheduleInfoDepRequestDto;
import com.konai.kurong.faketee.schedule.dto.ScheduleInfoResponseDto;
import com.konai.kurong.faketee.schedule.dto.ScheduleInfoSaveRequestDto;
import com.konai.kurong.faketee.schedule.dto.TemplatePositionResponseDto;
import com.konai.kurong.faketee.schedule.entity.ScheduleInfo;
import com.konai.kurong.faketee.schedule.entity.Template;
import com.konai.kurong.faketee.schedule.repository.schedule.ScheduleInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ScheduleInfoService {
    private final TemplateService templateService;
    private final EmployeeService employeeService;
    private final ScheduleInfoRepository scheduleInfoRepository;

    private static final String SCH_PREFIX = "SCH_";
    private static final String VAC_PREFIX = "VAC_";


    public List<EmployeeSchResponseDto> getEmpByDepAndPos(ScheduleInfoDepRequestDto requestDto) {
        List<TemplatePositionResponseDto> posList = templateService.loadTemplatePositions(requestDto.getTempId());
        List<Long> posIds = new ArrayList<>();
        for (int i = 0; i < posList.size(); i++) {
            Long posId = posList.get(i).getPosition().getId();
            posIds.add(posId);
        }

        List<EmployeeSchResponseDto> empList = employeeService.getEmpByDepAndPos(requestDto.getCheckedDep(), posIds);

        return empList;
    }

    @Transactional
    public void registerScheduleInfo(ScheduleInfoSaveRequestDto requestDto) {
        Template template = templateService.findById(requestDto.getTempId());
        List<ScheduleInfo> scheduleInfoList = new ArrayList<>();
        List<LocalDate> dateList = requestDto.transToDate();

        for (int i = 0; i < requestDto.getCheckedEmp().size(); i++) {
            Long empId = requestDto.getCheckedEmp().get(i);
            Employee employee = employeeService.findByEmployeeById(empId);
            for (int j = 0; j < dateList.size(); j++) {
                ScheduleInfo scheduleInfo = ScheduleInfo.builder()
                        .date(dateList.get(j))
                        .state(SCH_PREFIX + template.getName())
                        .startTime(template.getStartTime())
                        .endTime(template.getEndTime())
                        .employee(employee)
                        .build();
                scheduleInfoList.add(scheduleInfo);
            }
        }
        scheduleInfoRepository.saveAll(scheduleInfoList);
    }

    @Transactional
    public List<ScheduleInfoResponseDto> getSchListByDate(String date, Long corId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);

        List<ScheduleInfo> scheduleInfoList = scheduleInfoRepository.findAllByDateAndEmployeeCorporationId(dateTime, corId);

        return ScheduleInfoResponseDto.convertToDtoList(scheduleInfoList);
    }

    @Transactional
    public void deleteSchedule(Long schId){
        scheduleInfoRepository.deleteById(schId);
    }
}
