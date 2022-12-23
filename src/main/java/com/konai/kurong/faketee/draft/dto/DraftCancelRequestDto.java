package com.konai.kurong.faketee.draft.dto;

import com.konai.kurong.faketee.employee.utils.AuthIdsDto;
import com.konai.kurong.faketee.employee.utils.EmpAuthCheckList;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;
import java.util.ArrayList;
import java.util.List;

@Getter
@Builder
public class DraftCancelRequestDto implements AuthIdsDto {
    @NotNull
    private Long draftId;

    @Override
    public EmpAuthCheckList getEmpAuthCheckList() {
        List<Long> superIds = new ArrayList<>();
        superIds.add(getDraftId());
        return EmpAuthCheckList.builder()
                .idList(superIds)
                .build();
    }
}
