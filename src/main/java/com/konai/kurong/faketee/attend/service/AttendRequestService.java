package com.konai.kurong.faketee.attend.service;

import com.konai.kurong.faketee.attend.dto.AttendRequestDeleteDto;
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
import com.konai.kurong.faketee.utils.exception.custom.attend.request.DraftNotWaitException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.parameters.P;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@Service
@RequiredArgsConstructor
public class AttendRequestService {
    private final AttendRequestRepository attendRequestRepository;
    private final EmployeeRepository employeeRepository;
    private final DraftRepository draftRepository;
    private final AttendRepository attendRepository;

    //출퇴근기록 생성 요청
    public void createAttendRequest(AttendRequestSaveDto requestDto) {
//        기안(DRAFT) 만들기에 필요한 것들 불러오기
        Employee requestEmployee = employeeRepository.findById(requestDto.getReqEmpId()).orElseThrow();
        Employee approvalEmpFin = employeeRepository.findById(requestDto.getApvEmpFinId()).orElseThrow();
        String approvalMessage = requestDto.getRequestMessage();
        DraftCrudType crudType = DraftCrudType.CREATE;

//        출퇴근기록 요청(ATD_REQ) 만들기에 필요한 것들 불러오기
        LocalDate atdReqDate = new Date(requestDto.getAtdReqDate().getTime()).toLocalDate();
        LocalTime startTime = LocalTime.parse(requestDto.getStartTime());
        LocalTime endTime = LocalTime.parse(requestDto.getEndTime());

//        기안(DRAFT) Entity 만들기
        Draft draft = setOldAttendRequest(requestEmployee.getId(), atdReqDate, crudType);
        if (draft == null) {
            draft = saveDraft(requestEmployee, approvalEmpFin, approvalMessage, crudType);
        }

//        출퇴근기록 요청(ATD_REQ) Entity 만들기
        saveAttendRequest(atdReqDate, startTime, endTime, requestEmployee, draft);
    }

    //    출퇴근기록 수정 요청
    public void updateAttendRequest(AttendRequestUpdateDto requestDto) {
//        기안(DRAFT) 만들기에 필요한 것들 불러오기
        Employee requestEmployee = employeeRepository.findById(requestDto.getReqEmpId()).orElseThrow();
        Employee approvalEmpFin = employeeRepository.findById(requestDto.getApvEmpFinId()).orElseThrow();
        String approvalMessage = requestDto.getRequestMessage();
        DraftCrudType crudType = DraftCrudType.UPDATE;

//        출퇴근기록 요청(ATD_REQ) 만들기에 필요한 것들 불러오기
        LocalDate atdReqDate = new Date(requestDto.getAtdReqDate().getTime()).toLocalDate();
        LocalTime startTime = LocalTime.parse(requestDto.getStartTime());
        LocalTime endTime = LocalTime.parse(requestDto.getEndTime());

//        기안(DRAFT) Entity 만들기
        Draft draft = setOldAttendRequest(requestEmployee.getId(), atdReqDate, crudType);
        if (draft == null) {
            draft = saveDraft(requestEmployee, approvalEmpFin, approvalMessage, crudType);
        }

//        출퇴근기록 요청(ATD_REQ) Entity 만들기
        saveAttendRequest(atdReqDate, startTime, endTime, requestEmployee, draft);
    }

    //    출퇴근기록 삭제 요청
    public void deleteAttendRequest(AttendRequestDeleteDto requestDto) {
        //        기안(DRAFT) 만들기에 필요한 것들 불러오기
        Employee requestEmployee = employeeRepository.findById(requestDto.getReqEmpId()).orElseThrow();
        Employee approvalEmpFin = employeeRepository.findById(requestDto.getApvEmpFinId()).orElseThrow();
        String approvalMessage = requestDto.getRequestMessage();
        DraftCrudType crudType = DraftCrudType.DELETE;

//        출퇴근기록 요청(ATD_REQ) 만들기에 필요한 것들 불러오기
        LocalDate atdReqDate = new Date(requestDto.getAtdReqDate().getTime()).toLocalDate();

//        기안(DRAFT) Entity 만들기
        Draft draft = setOldAttendRequest(requestEmployee.getId(), atdReqDate, crudType);
        if (draft == null) {
            draft = saveDraft(requestEmployee, approvalEmpFin, approvalMessage, crudType);
        }

//        출퇴근기록 요청(ATD_REQ) Entity 만들기
        saveAttendRequest(atdReqDate, null, null, requestEmployee, draft);
    }

    //      기안(DRAFT) Entity 저장하기
    @Transactional
    public Draft saveDraft(Employee requestEmployee, Employee approvalEmpFin, String approvalMessage, DraftCrudType crudType) {
        Draft draft = Draft.builder()
                .approvalDate(null)     //  나중에 승인권자 승인하면 승인 날짜 넣어주기
                .requestDate(LocalDateTime.now())   //  요청을 보낸 날짜는 오늘
                .approvalMessage(null)     //   나중에 승인권자 승인하면 승인권자 메세지 넣어주기
                .stateCode(DraftStateCode.WAIT)     //  '대기'
                .requestMessage(approvalMessage)
                .requestType(DraftRequestType.ATTENDANCE)   //  '출퇴근기록'
                .crudType(crudType)
                .requestEmployee(requestEmployee)
                .approvalEmpFin(approvalEmpFin)
                .build();

        return draftRepository.save(draft);
    }

    //      출퇴근기록 요청(ATD_REQ) Entity 저장하기
    @Transactional
    public AttendRequest saveAttendRequest(LocalDate atdReqDate, LocalTime startTime, LocalTime endTime, Employee requestEmployee, Draft draft) {
        AttendRequest attendRequest = AttendRequest.builder()
                .atdReqDate(atdReqDate)
                .val(AttendRequestVal.T)    //  'Common'
                .startTime(startTime)
                .endTime(endTime)
                .employee(requestEmployee)
                .draft(draft)
                .build();

        return attendRequestRepository.save(attendRequest);
    }

//    ATD_REQ 이 새로 들어올 때마다 같은 직원이 같은 날짜에 대해서 ATD_REQ 을 보냈었는지 확인
//    null 이면 직원이 해당 날짜에 처음으로 ATD_REQ 을 보낸 것이므로 바로 CREATE
//    존재하는 Entity 가 있다면 이미 ATD_REQ 을 보냈으므로 과거의 ATD_REQ val 'U'로 UPDATE
//    만약 ATD_REQ 의 DRAFT 의 StateCode 가 'APVL_FIN' 이거나 'REJ_FIN' 이면 CREATE 가능하다
//    Draft StateCode 'WAIT' 일 때만 ATD_REQ 변경 가능 아닌 상태면 throw custom exception
//    Draft CrudType 새로운 ATD_REQ 에 맞게 UPDATE
//    Draft 찾아서 반환하기
//    후 새로운 ATD_REQ CREATE
    @Transactional
    public Draft setOldAttendRequest(Long empId, LocalDate atdReqDate, DraftCrudType crudType) {
        AttendRequest oldAttendRequest = attendRequestRepository.findAttendRequestByEmpDateVal(empId, atdReqDate).orElseThrow();
        if (oldAttendRequest != null) {
            oldAttendRequest.updateVal();
            Draft draft = draftRepository.findById(oldAttendRequest.getDraft().getId()).orElseThrow();

            if(draft.getStateCode() == DraftStateCode.WAIT) {
                draft.updateCrudType(crudType);
                return draft;
            } else {
                throw new DraftNotWaitException();
            }
        } else {
            return null;
        }
    }

//    Draft 취소하기
//    ATD_REQ 이 1개인 경우, Draft StateCode 'NOT_VALID' UPDATE / ATD_REQ val 'U' UPDATE
//    ATD_REQ 이 1개 이상인 경우, Draft StateCode 'NOT_VALID' UPDATE / ATD_REQ 에서 val 이 C인 행(1개만 존재)을 찾아서 'U' UPDATE
//    따라서 draftId 로 val 이 'C'인 ATD_REQ 찾아온다
//    Draft StateCode 'WAIT' 일 때만 ATD_REQ 취소 가능 아닌 상태면 throw custom exception
    @Transactional
    public void cancelDraft(Long draftId) {
        AttendRequest attendRequest = attendRequestRepository.findAttendRequestByDraftVal(draftId).orElseThrow();
        attendRequest.updateVal();

        Draft draft = draftRepository.findById(draftId).orElseThrow();
        if(draft.getStateCode() == DraftStateCode.WAIT) {
            draft.updateStateCode(DraftStateCode.NOT_VALID);
        } else {
            throw new DraftNotWaitException();
        }
    }

    /**
     * 출퇴근기록 생성 요청 시, 선택한 날짜에 기록이 있는지 없는지 확인
     * @param date : 선택한 날짜
     * @return 기록이 없으면 true 생성 요청 가능
     * @return 기록이 있으면 false 생성 요청 불가능
     */
    public boolean checkAtdRecord(String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate localDate = LocalDate.parse(date, formatter);
        return attendRepository.findByDate(localDate).orElse(null) == null ? true : false;
    }
}
