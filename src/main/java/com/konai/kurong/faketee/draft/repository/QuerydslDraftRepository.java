package com.konai.kurong.faketee.draft.repository;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;

import java.time.LocalDateTime;
import java.util.List;

public interface QuerydslDraftRepository {

    List<Draft> getDraftsWithRequestsByApproverAndStateCode(Long apvlEmpId);

    List<Draft> getDraftsWithRequestsByEmployeeAndStateCode(Long empId, List<DraftStateCode> draftStateCodeList);
    void updateDraftStateCode(Long draftId, DraftStateCode draftStateCode);
    List<Long> getDraftIdsByEmployeeId(Long empId);

    void updateDraftStateCodeAndDateAndMessageByApvlEmp(Long draftId, DraftStateCode draftStateCode, LocalDateTime dateTime, String apvlMsg);
}
