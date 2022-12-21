package com.konai.kurong.faketee.draft.service;

import com.konai.kurong.faketee.draft.dto.DraftResponseDto;
import com.konai.kurong.faketee.draft.dto.DraftUpdateRequestDto;
import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.repository.DraftRepository;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.konai.kurong.faketee.vacation.repository.VacRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.List;
@Transactional
@Slf4j
@Service
@RequiredArgsConstructor
public class DraftService {

    private final VacRequestRepository vacDateRequestRepository;
    private final DraftRepository draftRepository;

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
                }
            }
        } else {    // 1차 승인권자가 없을 경우
            if (draft.getApprovalEmpFin().getId().equals(empId)) {   // 최종 승인권자의 요청일 경우
                if (currentState.equals(DraftStateCode.WAIT)) {
                    draftRepository.updateDraftStateCodeAndDateAndMessageByApvlEmp(requestDto.getDraftId(), DraftStateCode.APVL_FIN, LocalDateTime.now(), requestDto.getApvlMsg());
                }
            }
        }
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
