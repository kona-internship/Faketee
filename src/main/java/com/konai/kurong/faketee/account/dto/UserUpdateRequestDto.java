package com.konai.kurong.faketee.account.dto;

import lombok.*;

@Builder
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Data
public class UserUpdateRequestDto {

    private String oldPassword;
    private String newPassword;

    public void setEncPassword(String encPassword){

        this.newPassword = encPassword;
    }
}
