package com.konai.kurong.faketee.attend.service;

import com.konai.kurong.faketee.attend.dto.AttendResponseDto;
import com.konai.kurong.faketee.attend.entity.Attend;
import com.konai.kurong.faketee.attend.repository.AttendRepository;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.service.EmployeeService;
import com.konai.kurong.faketee.attend.utils.AtdState;
import com.konai.kurong.faketee.schedule.dto.ScheduleInfoResponseDto;
import com.konai.kurong.faketee.schedule.entity.ScheduleInfo;
import com.konai.kurong.faketee.schedule.service.ScheduleInfoService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class AttendService {
    private final ScheduleInfoService scheduleInfoService;
    private final EmployeeService employeeService;
    private final AttendRepository attendRepository;

    /**
     * 홈페이지에서 사용자의 근태에 대한 현재 상태
     * 관련 데이터를 모두 가져온다.
     *
     * @param corId
     * @param date
     * @param userId
     * @return
     * @throws Exception
     */
    @Transactional
    public AttendResponseDto getUserScheduleInfo(Long corId, String date, Long userId) throws Exception {
        Employee emp = employeeService.getEmpByUserAndCor(userId, corId);
        ScheduleInfo scheduleInfo = scheduleInfoService.getSchByDateAndEmp(date, corId, emp.getId());
        String department = emp.getDepartment().getName();

        if (scheduleInfo == null) {
            return null;
        }
        Optional<Attend> attend = attendRepository.findAllByScheduleInfoId(scheduleInfo.getId());
        AttendResponseDto attendResponseDto;
        String state;
        if (attend.isPresent()) {
            //출근버튼을 찍었다는 말. 근무 중/초과근로 or 퇴근 완료
            Attend todayAtd = attend.get();
            state = checkAtdState(todayAtd.getEndTime(), scheduleInfo.getEndTime());
            log.info(state);
            attendResponseDto = AttendResponseDto.builder()
                    .schStartTime(scheduleInfo.getStartTime().toString())
                    .schEndTime(scheduleInfo.getEndTime().toString())
                    .depName(department)
                    .todaySch(scheduleInfo.getState())
                    .state(state)
                    .atdStartTime(todayAtd.getStartTime().toString())
                    .atdEndTime(String.valueOf(todayAtd.getEndTime()))
                    .build();

        } else {
            //출근버튼을 찍지않았다. 근무 전 or 지각 or 결근
            state = checkState(scheduleInfo.getStartTime(), scheduleInfo.getEndTime());
            log.info(state);

            attendResponseDto = AttendResponseDto.builder()
                    .schStartTime(scheduleInfo.getStartTime().toString())
                    .schEndTime(scheduleInfo.getEndTime().toString())
                    .depName(department)
                    .todaySch(ScheduleInfoResponseDto.schState(scheduleInfo))
                    .state(state)
                    .build();
        }

        return attendResponseDto;
    }

    /**
     * 출근버튼을 찍은 상태에서
     * 사용자가 현재 근무 중인지 초과근무인지 퇴근완료인지
     * 상태값을 반환해준다.
     * @param atdEndTime
     * @param schEndTime
     * @return
     */
    public String checkAtdState(LocalTime atdEndTime, LocalTime schEndTime) {
        LocalTime present = LocalTime.now();
        if (String.valueOf(atdEndTime) != null) {
            return AtdState.OFF_WORK.state();
        } else if (present.isBefore(schEndTime)) {
            return AtdState.AT_WORK.state();
        } else {
            return AtdState.OVER_WORK.state();
        }
    }

    /**
     * 출근버튼을 찍지 않은 상태에서
     * 사용자가 현재 근무 전인지 지각인지 결근이지를 보고
     * 상태값을 반환해준다.
     * @param schStartTime
     * @param schEndTime
     * @return
     */
    public String checkState(LocalTime schStartTime, LocalTime schEndTime) {
        LocalTime present = LocalTime.now();

        if (present.isBefore(schStartTime)) {
            return AtdState.BF_WORK.state();
        } else if (present.isAfter(schEndTime)) {
            return AtdState.ABSENCE.state();
        } else {
            return AtdState.LATE.state();
        }
    }

    /**
     * 출근/퇴근 버튼을 클릭하여
     * 출근 시에는 attend 새로 생성,
     * 퇴근 시에는 기존 attend를 update한다.
     *
     * @param corId
     * @param userId
     * @param onOff
     * @throws Exception
     */
    @Transactional
    public void clickAtd(Long corId, Long userId, String onOff) throws Exception {
        Employee emp = employeeService.getEmpByUserAndCor(userId, corId);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

        ScheduleInfo scheduleInfo = scheduleInfoService.getSchByDateAndEmp(LocalDate.now().format(formatter), corId, emp.getId());
        if(onOff.equals("on")){
            //행 새로 생성
            Attend attend = Attend.builder()
                    .startTime(LocalTime.now())
                    .endTime(null)
                    .val("T")
                    .date(LocalDate.now())
                    .employee(emp)
                    .scheduleInfo(scheduleInfo)
                    .build();

            attendRepository.save(attend);
        }else {
            //행 업데이트
            attendRepository.updateAtdEndTime(scheduleInfo.getId(), LocalTime.now());
        }
    }
}
