package com.konai.kurong.faketee.draft.dto;

import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotNull;

@Getter
@Builder
public class DraftUpdateRequestDto {
    @NotNull
    private Long draftId;
    private String apvlMsg;
}
