package com.konai.kurong.faketee.attend.service;

import com.konai.kurong.faketee.attend.dto.AttendRequestSaveDto;
import com.konai.kurong.faketee.attend.dto.AttendRequestUpdateDto;
import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.konai.kurong.faketee.attend.repository.AttendRepository;
import com.konai.kurong.faketee.attend.repository.AttendRequestRepository;
import com.konai.kurong.faketee.attend.utils.AttendRequestVal;
import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.repository.DraftRepository;
import com.konai.kurong.faketee.draft.utils.DraftCrudType;
import com.konai.kurong.faketee.draft.utils.DraftRequestType;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.konai.kurong.faketee.employee.entity.Employee;
import com.konai.kurong.faketee.employee.repository.EmployeeRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendRequestService {

    private final AttendRequestRepository attendRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final DraftRepository draftRepository;
    private final AttendRepository attendRepository;

    //출퇴근기록 생성 요청
    @Transactional
    public void createAttendRequest(AttendRequestSaveDto requestDto) {

//        기안(DRAFT) 만들기에 필요한 것들 불러오기
        LocalDateTime requestDate = LocalDateTime.now();
        DraftStateCode draftStateCode = DraftStateCode.WAIT;
        DraftRequestType draftRequestType = DraftRequestType.ATTENDANCE;
        DraftCrudType draftCrudType = DraftCrudType.CREATE;
        Employee requestEmployee = employeeRepository.findById(requestDto.getReqEmpId()).orElseThrow();

//        기안(DRAFT) Entity 만들기
        Draft draft = Draft.builder()
                .approvalDate(null)     //  나중에 승인권자 승인하면 승인 날짜 넣어주기
                .requestDate(requestDate)
                .approvalMessage(null)     //   나중에 승인권자 승인하면 승인권자 메세지 넣어주기
                .stateCode(draftStateCode)
                .requestMessage(requestDto.getRequestMessage())
                .requestType(draftRequestType)
                .crudType(draftCrudType)
                .requestEmployee(requestEmployee)
                .build();


//        출퇴근기록 요청(ATD_REQ) 만들기에 필요한 것들 불러오기
//        AttendRequestVal val = AttendRequestVal.C;
//        LocalTime startTime = LocalTime.parse(requestDto.getStartTime());
//        LocalTime endTime = LocalTime.parse(requestDto.getEndTime());
//        Employee employee = employeeRepository.findById(requestDto.getReqEmpId()).orElseThrow();
//        Draft draft = draftRepository.findById(requestDto.getDraftId()).orElseThrow();
//
////        출퇴근기록 요청(ATD_REQ) Entity 만들기
//        AttendRequest attendRequest = AttendRequest.builder()
//                .date(new Date(requestDto.getDate().getTime()).toLocalDate())
//                .val(val.getVal())
//                .startTime(startTime)
//                .endTime(endTime)
//                .employee(employee)
//                .draft(draft)
//                .build();

//        출퇴근기록 요청(ATD_REQ) Entity 저장하기
//        attendRequestRepository.save(attendRequest);

//        ######근태 테이블에 해당 날짜에 해당 출퇴근 기록 관련된 정보를 저장해줘야 함######
    }

//    출퇴근기록 수정 요청
    @Transactional
    public void updateAttendRequest(AttendRequestUpdateDto requestDto) {

    }

//    출퇴근기록 삭제 요청
}
