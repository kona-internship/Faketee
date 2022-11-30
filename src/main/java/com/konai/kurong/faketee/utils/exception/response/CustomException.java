package com.konai.kurong.faketee.utils.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@JsonInclude(JsonInclude.Include.ALWAYS)
public class CustomException {
    private String field;
    private String message;
}