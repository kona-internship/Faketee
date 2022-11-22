package com.konai.kurong.faketee.account.dto;

import com.konai.kurong.faketee.account.entity.User;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class UserUpdateRequestDto {

    private String oldPassword;
    private String newPassword;
}
