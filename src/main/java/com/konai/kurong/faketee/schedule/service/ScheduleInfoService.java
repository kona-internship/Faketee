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
import com.konai.kurong.faketee.utils.exception.custom.attend.request.NoSchInfoException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
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

    /**
     * 템플릿 id를 받아 템플릿을 찾는다.
     * 선택된 조직과 템플릿에 적용된 직무에
     * 모두 만족하는 emp리스트를 반환
     *
     * @param requestDto 선택된 조직들의 id리스트와 템플릿 id
     * @return
     */
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

    /**
     * 근무일정 추가
     * 선택한 직원들과 선택된 날짜에 해당하는
     * 템플릿 내용을 근무일정을 생성해준다.
     *
     * @param requestDto 템플릿 id, 날짜 리스트, 직원 리스트
     */
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

    /**
     * 해당날짜에 해당하는 근무일정을 가져온다.
     *
     * @param date  날짜
     * @param corId 회사
     * @return
     */
    @Transactional
    public List<ScheduleInfoResponseDto> getSchListByDate(String date, Long corId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);

        List<ScheduleInfo> scheduleInfoList = scheduleInfoRepository.findAllByDateAndEmployeeCorporationId(dateTime, corId);

        return ScheduleInfoResponseDto.convertToDtoList(scheduleInfoList);
    }

    /**
     * 근로일정 찾기
     * 날짜, 회사id, 직원id
     *
     * @param date
     * @param corId
     * @param empId
     * @return
     */
    @Transactional
    public ScheduleInfo getSchByDateAndEmp(String date, Long corId, Long empId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);

        return scheduleInfoRepository.findAllByDateAndEmployeeCorporationIdAndEmployeeId(dateTime, corId, empId);
    }

    @Transactional
    public ScheduleInfo getScheduleByDateAndEmp(String date, Long corId, Long empId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate dateTime = LocalDate.parse(date, formatter);
        ScheduleInfo scheduleInfo =  scheduleInfoRepository.findAllByDateAndEmployeeCorporationIdAndEmployeeId(dateTime, corId, empId);
        if(scheduleInfo == null) {
            throw new NoSchInfoException();
        }
        return scheduleInfo;
    }

    /**
     * 근무일정 삭제
     *
     * @param schId 삭제할 근무일정 id
     */
    @Transactional
    public void deleteSchedule(Long schId) {
        scheduleInfoRepository.deleteById(schId);
    }
}
