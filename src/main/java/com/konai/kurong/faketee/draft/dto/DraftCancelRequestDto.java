package com.konai.kurong.faketee.draft.dto;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class DraftCancelRequestDto {
    private Long draftId;
}
