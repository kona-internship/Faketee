package com.konai.kurong.faketee.corporation.dto;

import com.konai.kurong.faketee.corporation.entity.Corporation;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CorporationSaveRequestDto {
    @NotBlank(message = "회사 이름은 필수 입력값입니다.")
    private String name;

    public Corporation toEntity(){
        return Corporation.builder()
                .name(name)
                .createdDateTime(LocalDateTime.now())
                /*생성사 id 임의로 100L넣어둠*/
                .createdId("100L")
                .build();
    }
}
