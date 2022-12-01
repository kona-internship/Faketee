package com.konai.kurong.faketee.account.dto;

import lombok.*;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class EmailAuthRequestDto {

    private String email;
    private String emailAuthToken;
}
