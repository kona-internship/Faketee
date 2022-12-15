package com.konai.kurong.faketee.draft.service;

import com.konai.kurong.faketee.draft.dto.DraftResponseDto;
import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.repository.DraftRepository;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;
import com.konai.kurong.faketee.vacation.repository.VacDateRequestRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class DraftService {

    private final VacDateRequestRepository vacDateRequestRepository;
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
        draftRepository.updateDraftStateCode(draftId, DraftStateCode.NOT_VALID);
    }

    public DraftResponseDto getDraft(Long draftId){
        return DraftResponseDto.convertToDto(draftRepository.findById(draftId).orElseThrow(()->new IllegalArgumentException()));
    }
}
