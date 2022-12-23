package com.konai.kurong.faketee.draft.service;

import com.konai.kurong.faketee.attend.entity.Attend;
import com.konai.kurong.faketee.attend.entity.AttendRequest;
import com.konai.kurong.faketee.attend.repository.AttendRepository;
import com.konai.kurong.faketee.attend.repository.AttendRequestRepository;
import com.konai.kurong.faketee.draft.dto.DraftResponseDto;
import com.konai.kurong.faketee.draft.dto.DraftUpdateRequestDto;
import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.repository.DraftRepository;
import com.konai.kurong.faketee.draft.utils.DraftCrudType;
import com.konai.kurong.faketee.draft.utils.DraftRequestType;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.konai.kurong.faketee.schedule.entity.ScheduleInfo;
import com.konai.kurong.faketee.schedule.repository.schedule.ScheduleInfoRepository;
import com.konai.kurong.faketee.vacation.entity.VacRequest;
import com.konai.kurong.faketee.vacation.repository.VacRequestRepository;
import com.konai.kurong.faketee.vacation.repository.vac_info.VacInfoRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

import static com.konai.kurong.faketee.schedule.entity.QScheduleInfo.scheduleInfo;

@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class DraftService {

    private final DraftRepository draftRepository;
    private final VacInfoRepository vacInfoRepository;
    private final AttendRequestRepository attendRequestRepository;
    private final ScheduleInfoRepository scheduleInfoRepository;
    private final AttendRepository attendRepository;

    /**
     * 승인권자가 자신이 승인 검토해야하는 기안 목록을 가져온다.
     *
     * @param empId
     * @return
     */
    public List<DraftResponseDto> getApvlDraftList(Long empId){
        List<Draft> draftList =  draftRepository.getDraftsWithRequestsByApproverAndStateCode(empId, DraftStateCode.getWaitingForApproval());
        return DraftResponseDto.convertToDtoList(draftList);
    }

    /**
     * 직원이 요청이 승인되거나 반려되어 검토가 완료된 목록을 가져온다.
     *
     * @param empId
     * @return
     */
    public List<DraftResponseDto> getDoneDraftList(Long empId){
        List<Draft> draftList =  draftRepository.getDraftsWithRequestsByEmployeeAndStateCode(empId, DraftStateCode.getDoneApproval());
        return DraftResponseDto.convertToDtoList(draftList);
    }

    /**
     * 직원이 자신이 요청한 요청 목록을 가져온다.
     *
     * @param empId
     * @return
     */
    public List<DraftResponseDto> getReqDraftList(Long empId){
        List<Draft> draftList =  draftRepository.getDraftsWithRequestsByEmployeeAndStateCode(empId, DraftStateCode.getWaitingForApproval());
        return DraftResponseDto.convertToDtoList(draftList);
    }

    public void cancelDraft(Long draftId){
        // 캔슬 할 때 상태 확인(대기중일 때만)
        draftRepository.updateDraftStateCode(draftId, DraftStateCode.NOT_VALID);
    }

    public DraftResponseDto getDraft(Long draftId){
        return DraftResponseDto.convertToDto(draftRepository.findById(draftId).orElseThrow(()->new IllegalArgumentException()));
    }

    public void apvlDraft(Long empId, DraftUpdateRequestDto requestDto){
        Draft draft = draftRepository.findById(requestDto.getDraftId()).orElseThrow(()->new IllegalArgumentException());
        DraftStateCode currentState = draft.getStateCode();

        if(draft.getApprovalEmp1() != null) {   // 1차 승인권자가 있을 경우
            if (draft.getApprovalEmp1().getId().equals(empId)) {    // 1차 승인권자의 요청일 경우
                if (currentState.equals(DraftStateCode.WAIT)) {     // 상태코드가 대기중일 경우
                    draftRepository.updateDraftStateCodeAndDateAndMessageByApvlEmp(requestDto.getDraftId(), DraftStateCode.APVL_1, LocalDateTime.now(), requestDto.getApvlMsg());
                }
            } else if (draft.getApprovalEmpFin().getId().equals(empId)) {   // 최종 승인권자의 요청일 경우
                if (currentState.equals(DraftStateCode.APVL_1)) {
                    draftRepository.updateDraftStateCodeAndDateAndMessageByApvlEmp(requestDto.getDraftId(), DraftStateCode.APVL_FIN, LocalDateTime.now(), requestDto.getApvlMsg());
                    applyDraftToSchedule(draft);
                }
            }
        } else {    // 1차 승인권자가 없을 경우
            if (draft.getApprovalEmpFin().getId().equals(empId)) {   // 최종 승인권자의 요청일 경우
                if (currentState.equals(DraftStateCode.WAIT)) {
                    draftRepository.updateDraftStateCodeAndDateAndMessageByApvlEmp(requestDto.getDraftId(), DraftStateCode.APVL_FIN, LocalDateTime.now(), requestDto.getApvlMsg());
                    applyDraftToSchedule(draft);
                }
            }
        }
    }

    public void applyDraftToSchedule(Draft draft){

        if(draft.getRequestType().equals(DraftRequestType.ATTENDANCE)){
            List<AttendRequest> attendRequestList = attendRequestRepository.getAtdReqByDraftId(draft.getId());
            for(AttendRequest atdReq: attendRequestList){
                switch (draft.getCrudType()){
                    case CREATE:
                        List<ScheduleInfo> orgSchInfos = scheduleInfoRepository.findAllByDateAndEmployeeId(atdReq.getAtdReqDate(), atdReq.getEmployee().getId());
                        ScheduleInfo reqSchInfo = null;
                        for(ScheduleInfo schInfo : orgSchInfos){
                            if(schInfo.getState().contains("ATD")){
                                reqSchInfo = schInfo;
                                break;
                            }
                        }
                        if(reqSchInfo != null){
                            attendRepository.save(Attend.builder()
                                    .date(atdReq.getAtdReqDate())
                                    .startTime(atdReq.getStartTime())
                                    .endTime(atdReq.getEndTime())
                                    .employee(atdReq.getEmployee())
                                    .scheduleInfo(reqSchInfo)
                                    .val("T")
                                    .build()
                            );
                        }
                        break;
                    case UPDATE:
                        List<Attend> updateAttends = attendRepository.findAllByDateAndEmployeeId(atdReq.getAtdReqDate(), atdReq.getEmployee().getId());
                        if(updateAttends.size() == 1){
                            Attend attend = updateAttends.get(0);
                            attend.changeTimes(atdReq.getStartTime(), atdReq.getEndTime());
                        }
                        break;
                    case DELETE:
                        List<Attend> deleteAttends = attendRepository.findAllByDateAndEmployeeId(atdReq.getAtdReqDate(), atdReq.getEmployee().getId());
                        if(deleteAttends.size() == 1){
                            Attend attend = deleteAttends.get(0);
                            attendRepository.delete(attend);
                        }
                        break;
                }
            }
        } else if (draft.getRequestType().equals(DraftRequestType.VACATION)) {
            List<VacRequest> attendRequestList = vacInfoRepository.getVacReqByDraftId(draft.getId());
            for (VacRequest vacReq : attendRequestList) {
                List<ScheduleInfo> orgSchInfos = scheduleInfoRepository.findAllByDateAndEmployeeId(vacReq.getDate().toLocalDate(), vacReq.getEmployee().getId());
                switch (draft.getCrudType()) {
                    case CREATE:
                        ScheduleInfo orgSchInfo = null;
                        for (ScheduleInfo schInfo : orgSchInfos) {
                            if (schInfo.getState().contains("SCH")) {
                                orgSchInfo = schInfo;
                                break;
                            }
                        }
                        if (orgSchInfo == null) {
                            break;
                        }
                        if (vacReq.getVacType().getStartTime().compareTo(orgSchInfo.getStartTime()) == 0) {
                            orgSchInfo.changeTimes(vacReq.getVacType().getEndTime(), orgSchInfo.getEndTime());
                        } else if (vacReq.getVacType().getEndTime().compareTo(orgSchInfo.getEndTime()) == 0) {
                            orgSchInfo.changeTimes(orgSchInfo.getStartTime(), vacReq.getVacType().getStartTime());
                        }
                        scheduleInfoRepository.save(ScheduleInfo.builder()
                                .date(vacReq.getDate().toLocalDate())
                                .startTime(vacReq.getVacType().getStartTime())
                                .endTime(vacReq.getVacType().getEndTime())
                                .state("VAC_" + vacReq.getVacType().getName())
                                .build());
                        break;
                    case UPDATE:
                        ScheduleInfo updateVacSchInfo = null;
                        ScheduleInfo updateAtdSchInfo = null;
                        LocalTime totalStartTime = null;
                        LocalTime totalEndTime = null;
                        for (ScheduleInfo schInfo : orgSchInfos) {
                            if (schInfo.getState().contains("VAC")) {
                                updateVacSchInfo = schInfo;
                            }
                            if (schInfo.getState().contains("SCH")) {
                                updateAtdSchInfo = schInfo;
                            }
                        }
                        if (updateVacSchInfo.getStartTime().compareTo(updateAtdSchInfo.getStartTime()) > 0) {
                            totalStartTime = updateAtdSchInfo.getStartTime();
                            totalEndTime = updateVacSchInfo.getEndTime();
                        } else {
                            totalStartTime = updateVacSchInfo.getStartTime();
                            totalEndTime = updateAtdSchInfo.getEndTime();
                        }
                        if (vacReq.getVacType().getStartTime().compareTo(totalStartTime) == 0) {
                            updateAtdSchInfo.changeTimes(vacReq.getVacType().getEndTime(), totalEndTime);
                            updateVacSchInfo.changeTimes(vacReq.getVacType().getStartTime(), vacReq.getVacType().getEndTime());
                        } else if (vacReq.getVacType().getEndTime().compareTo(totalEndTime) == 0) {
                            updateAtdSchInfo.changeTimes(totalStartTime, vacReq.getVacType().getStartTime());
                            updateVacSchInfo.changeTimes(vacReq.getVacType().getStartTime(), vacReq.getVacType().getEndTime());
                        }
                        break;
                    case DELETE:
                        ScheduleInfo deleteVacSchInfo = null;
                        ScheduleInfo atdSchInfo = null;
                        LocalTime ttlStartTime = null;
                        LocalTime ttlEndTime = null;
                        for (ScheduleInfo schInfo : orgSchInfos) {
                            if (schInfo.getState().contains("VAC")) {
                                deleteVacSchInfo = schInfo;
                            }
                            if (schInfo.getState().contains("SCH")) {
                                atdSchInfo = schInfo;
                            }
                        }
                        if (deleteVacSchInfo.getStartTime().compareTo(atdSchInfo.getStartTime()) > 0) {
                            ttlStartTime = atdSchInfo.getStartTime();
                            ttlEndTime = deleteVacSchInfo.getEndTime();
                        } else {
                            ttlStartTime = deleteVacSchInfo.getStartTime();
                            ttlEndTime = atdSchInfo.getEndTime();
                        }
                        if(deleteVacSchInfo != null) {
                            scheduleInfoRepository.delete(deleteVacSchInfo);
                            atdSchInfo.changeTimes(ttlStartTime, ttlEndTime);
                        }
                }
            }
        }
    }

    public void divideTime(LocalTime newStartTime, LocalTime newEndTime, LocalTime originStartTime, LocalTime originEndTime){

    }

    public void rejectDraft(Long empId, DraftUpdateRequestDto requestDto){
        Draft draft = draftRepository.findById(requestDto.getDraftId()).orElseThrow(()->new IllegalArgumentException());
        DraftStateCode currentState = draft.getStateCode();

        if(draft.getApprovalEmp1() != null) {   // 1차 승인권자가 있을 경우
            if (draft.getApprovalEmp1().getId().equals(empId)) {    // 1차 승인권자의 거절일 경우
                if (currentState.equals(DraftStateCode.WAIT)) {     // 상태코드가 대기중일 경우
                    draftRepository.updateDraftStateCodeAndDateAndMessageByApvlEmp(requestDto.getDraftId(), DraftStateCode.REJ_1, LocalDateTime.now(), requestDto.getApvlMsg());
                }
            } else if (draft.getApprovalEmpFin().getId().equals(empId)) {   // 최종 승인권자의 거절일 경우
                if (currentState.equals(DraftStateCode.APVL_1)) {
                    draftRepository.updateDraftStateCodeAndDateAndMessageByApvlEmp(requestDto.getDraftId(), DraftStateCode.REJ_FIN, LocalDateTime.now(), requestDto.getApvlMsg());
                }
            }
        } else {    // 1차 승인권자가 없을 경우
            if (draft.getApprovalEmpFin().getId().equals(empId)) {   // 최종 승인권자의 거질일 경우
                if (currentState.equals(DraftStateCode.WAIT)) {
                    draftRepository.updateDraftStateCodeAndDateAndMessageByApvlEmp(requestDto.getDraftId(), DraftStateCode.REJ_FIN, LocalDateTime.now(), requestDto.getApvlMsg());
                }
            }
        }
    }
}
