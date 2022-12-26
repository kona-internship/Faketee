package com.konai.kurong.faketee.draft.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DraftUpdateRequestDto {
    @NotNull
    private Long draftId;
    private String apvlMsg;
}
