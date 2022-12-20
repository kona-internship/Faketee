package com.konai.kurong.faketee.draft.repository;

import com.konai.kurong.faketee.draft.entity.Draft;
import com.konai.kurong.faketee.draft.utils.DraftStateCode;

import java.util.List;

public interface QuerydslDraftRepository {
    List<Draft> getDraftsWithRequestsByApproverAndStateCode(Long apvlEmpId, List<DraftStateCode> draftStateCodeList);
    List<Draft> getDraftsWithRequestsByEmployeeAndStateCode(Long empId, List<DraftStateCode> draftStateCodeList);
    void updateDraftStateCode(Long draftId, DraftStateCode draftStateCode);
    List<Long> getDraftIdsByEmployeeId(Long empId);
}
