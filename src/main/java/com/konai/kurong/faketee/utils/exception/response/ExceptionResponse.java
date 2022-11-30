package com.konai.kurong.faketee.utils.exception.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor()
@NoArgsConstructor()
@JsonInclude(JsonInclude.Include.ALWAYS)
public class ExceptionResponse {
    private String message;
    @Builder.Default
    private List<CustomException> errors = new ArrayList<>();

    public List<CustomException> addError(CustomException error) {
        this.errors.add(error);
        return this.errors;
    }
}